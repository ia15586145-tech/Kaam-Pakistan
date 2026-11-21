package com.example

import android.app.Application
import android.os.Looper
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.KaamDatabase
import com.example.data.prefs.KaamPrefsStore
import com.example.ui.KaamPakistanApp
import com.example.ui.KaamViewModel
import com.example.ui.LocationViewModel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class WorkerRatingAndReviewTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        KaamDatabase.resetForTests()
        KaamPrefsStore.resetForTests()
    }

    @After
    fun tearDown() {
        KaamDatabase.resetForTests()
        KaamPrefsStore.resetForTests()
    }

    @Test
    fun `worker detail screen displays average star rating breakdown and updates live when reviews are submitted`() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = KaamViewModel(app)
        val locationViewModel = LocationViewModel()

        composeRule.setContent {
            KaamPakistanApp(
                viewModel = viewModel,
                locationViewModel = locationViewModel,
                showSplashOnLaunch = false
            )
        }

        // Wait for seeded workers to load and scroll to first worker card
        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.filteredWorkers.isNotEmpty()
        }
        composeRule.onNodeWithTag("home_feed_list")
            .performScrollToNode(hasTestTag("worker_card_wrk_demo_1"))
        composeRule.onNodeWithTag("worker_card_wrk_demo_1").performClick()
        composeRule.waitForIdle()

        // Rating Summary Card initially shows 0.0 average & 0 reviews
        composeRule.onNodeWithTag("worker_detail_list")
            .performScrollToNode(hasTestTag("rating_summary_card"))
        composeRule.onNodeWithTag("rating_summary_card").assertIsDisplayed()
        composeRule.onNodeWithTag("worker_avg_rating_text").assertTextEquals("0.0")
        composeRule.onNodeWithTag("worker_review_count_text").assertTextEquals("0 reviews")

        // Tap quick star 4 -> opens LeaveReviewDialog pre-selected at 4 / 5
        composeRule.onNodeWithTag("quick_rate_star_4").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithText("4 / 5").assertIsDisplayed()
        composeRule.onNodeWithTag("review_comment_input").performTextInput("Quick and neat DB panel wiring.")
        composeRule.onNodeWithTag("confirm_review_btn").performClick()

        // Wait until first review (4 stars) is saved and averageRating becomes 4.0
        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.reviews.any { it.workerId == "wrk_demo_1" }
        }
        composeRule.onNodeWithTag("worker_detail_list")
            .performScrollToNode(hasTestTag("rating_summary_card"))
        composeRule.onNodeWithTag("worker_avg_rating_text").assertTextEquals("4.0")
        composeRule.onNodeWithTag("worker_review_count_text").assertTextEquals("1 reviews")
        composeRule.onNodeWithTag("worker_detail_list")
            .performScrollToNode(hasText("Quick and neat DB panel wiring."))
        composeRule.onNodeWithText("Quick and neat DB panel wiring.").assertIsDisplayed()

        // Submit second review (5 stars) via summary button -> averageRating updates to 4.5
        composeRule.onNodeWithTag("worker_detail_list")
            .performScrollToNode(hasTestTag("rating_summary_card"))
        composeRule.onNodeWithTag("rate_worker_summary_btn").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("star_rating_5").performClick()
        composeRule.onNodeWithTag("review_comment_input").performTextInput("Very polite and punctual!")
        composeRule.onNodeWithTag("confirm_review_btn").performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.reviews.count { it.workerId == "wrk_demo_1" } == 2
        }

        val stats = viewModel.uiState.value.allWorkers.first { it.worker.id == "wrk_demo_1" }
        assertEquals(2, stats.reviewCount)
        assertEquals(4.5, stats.averageRating, 0.01)

        composeRule.onNodeWithTag("worker_detail_list")
            .performScrollToNode(hasTestTag("rating_summary_card"))
        composeRule.onNodeWithTag("worker_avg_rating_text").assertTextEquals("4.5")
        composeRule.onNodeWithTag("worker_review_count_text").assertTextEquals("2 reviews")
        composeRule.onNodeWithTag("worker_detail_list")
            .performScrollToNode(hasText("Very polite and punctual!"))
        composeRule.onNodeWithText("Very polite and punctual!").assertIsDisplayed()
    }
}
