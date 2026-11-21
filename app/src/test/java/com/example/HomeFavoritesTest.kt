package com.example

import android.app.Application
import android.os.Looper
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.KaamDatabase
import com.example.data.prefs.KaamPrefsStore
import com.example.ui.KaamPakistanApp
import com.example.ui.KaamViewModel
import com.example.ui.LocationViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class HomeFavoritesTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Before
    fun setUp() {
        KaamDatabase.resetForTests()
        KaamPrefsStore.resetForTests()
        val app = ApplicationProvider.getApplicationContext<Application>()
        runBlocking {
            com.example.data.prefs.UserPreferencesRepository(app).setFavoriteWorkerIds(emptyList())
        }
    }

    @After
    fun tearDown() {
        KaamDatabase.resetForTests()
        KaamPrefsStore.resetForTests()
    }

    @Test
    fun `home screen heart toggle saves workers to persisted favorites list and enables quick access filtering`() {
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

        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.filteredWorkers.isNotEmpty()
        }

        // Initially no favorites are saved
        assertTrue(viewModel.uiState.value.favoriteWorkerIds.isEmpty())
        assertTrue(viewModel.uiState.value.favoriteWorkers.isEmpty())

        // Scroll to worker_card_wrk_demo_1 and tap its heart icon toggle
        composeRule.onNodeWithTag("home_feed_list")
            .performScrollToNode(hasTestTag("worker_card_wrk_demo_1"))
        composeRule.onNodeWithTag("favorite_toggle_wrk_demo_1").assertIsDisplayed().performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            "wrk_demo_1" in viewModel.uiState.value.favoriteWorkerIds &&
                viewModel.uiState.value.favoriteWorkers.any { it.worker.id == "wrk_demo_1" } &&
                "wrk_demo_1" in viewModel.uiState.value.preferences.favoriteWorkerIds
        }

        // Scroll to the Saved Favorites Quick-Access section on Home Screen
        composeRule.onNodeWithTag("home_feed_list")
            .performScrollToNode(hasTestTag("saved_favorites_section"))
        composeRule.onNodeWithTag("saved_favorites_section").assertIsDisplayed()
        composeRule.onNodeWithTag("favorite_quick_card_wrk_demo_1").assertIsDisplayed()

        // Save a second worker from the nearby list
        composeRule.onNodeWithTag("home_feed_list")
            .performScrollToNode(hasTestTag("worker_card_wrk_demo_2"))
        composeRule.onNodeWithTag("favorite_toggle_wrk_demo_2").performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.favoriteWorkerIds == listOf("wrk_demo_2", "wrk_demo_1") &&
                viewModel.uiState.value.preferences.favoriteWorkerIds == listOf("wrk_demo_2", "wrk_demo_1")
        }

        // Filter the worker list using the Favorites quick-access chip
        composeRule.onNodeWithTag("home_feed_list")
            .performScrollToNode(hasTestTag("home_search_filter_bar"))
        composeRule.onNodeWithTag("filter_favorites_chip").performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.showFavoritesOnly &&
                viewModel.uiState.value.filteredWorkers.size == 2
        }
        assertEquals(
            setOf("wrk_demo_1", "wrk_demo_2"),
            viewModel.uiState.value.filteredWorkers.map { it.worker.id }.toSet()
        )

        // Toggle wrk_demo_1 off via the quick-access favorite heart button
        composeRule.onNodeWithTag("home_feed_list")
            .performScrollToNode(hasTestTag("saved_favorites_section"))
        composeRule.onNodeWithTag("saved_favorites_row")
            .performScrollToNode(hasTestTag("favorite_quick_card_wrk_demo_1"))
        composeRule.onNodeWithTag("quick_favorite_toggle_wrk_demo_1").performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.favoriteWorkerIds == listOf("wrk_demo_2") &&
                viewModel.uiState.value.preferences.favoriteWorkerIds == listOf("wrk_demo_2")
        }
        assertFalse("wrk_demo_1" in viewModel.uiState.value.favoriteWorkerIds)

        // Verify DataStore persistence across a fresh repository/ViewModel instance
        val persistedIds = runBlocking {
            com.example.data.prefs.UserPreferencesRepository(app).preferencesFlow.first().favoriteWorkerIds
        }
        assertEquals(listOf("wrk_demo_2"), persistedIds)
    }
}
