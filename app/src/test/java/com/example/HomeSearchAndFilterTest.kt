package com.example

import android.app.Application
import android.os.Looper
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
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
class HomeSearchAndFilterTest {

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
    fun `home search and filter bar filters workers by skill category and cascading province district and resets cleanly`() {
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

        // Wait until seeded workers load
        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.filteredWorkers.isNotEmpty()
        }

        val initialCount = viewModel.uiState.value.filteredWorkers.size
        assertTrue(initialCount >= 10)

        // Verify Search & Filter Bar elements are rendered
        composeRule.onNodeWithTag("home_search_filter_bar").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithTag("home_search_input").assertIsDisplayed()
        composeRule.onNodeWithTag("filter_skill_category_chip").assertIsDisplayed()
        composeRule.onNodeWithTag("cascading_province_chip").assertIsDisplayed()
        composeRule.onNodeWithTag("cascading_district_chip").assertIsDisplayed()

        // 1. Search by natural skill keyword "plumbing" -> matches plumber workers
        composeRule.onNodeWithTag("home_search_input").performTextInput("plumbing")
        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.searchQuery == "plumbing" &&
                viewModel.uiState.value.filteredWorkers.isNotEmpty()
        }
        assertTrue(
            viewModel.uiState.value.filteredWorkers.all {
                it.worker.categoryKeys.contains("plumber") ||
                    it.worker.skillsSummary.lowercase().contains("plumbing")
            }
        )

        // Clear search text before combining chip filters
        composeRule.onNodeWithTag("clear_search_query_btn").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.filteredWorkers.size == initialCount
        }

        // 2. Filter by Skill Category picker -> Electric ("electrician")
        composeRule.onNodeWithTag("filter_skill_category_chip").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("skill_category_picker_sheet").assertIsDisplayed()
        composeRule.onNodeWithTag("skill_opt_electrician").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.selectedCategoryKey == "electrician"
        }
        assertTrue(viewModel.uiState.value.filteredWorkers.isNotEmpty())
        assertTrue(
            viewModel.uiState.value.filteredWorkers.all {
                it.worker.categoryKeys.contains("electrician")
            }
        )

        // 3. Filter by Cascading Location (Province -> Sindh -> Karachi Division -> Karachi East)
        composeRule.onNodeWithTag("cascading_province_chip").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("location_picker_list")
            .performScrollToNode(hasTestTag("province_opt_sindh"))
        composeRule.onNodeWithTag("province_opt_sindh").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("location_picker_list")
            .performScrollToNode(hasTestTag("division_opt_sindh_div_karachi"))
        composeRule.onNodeWithTag("division_opt_sindh_div_karachi").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("location_picker_list")
            .performScrollToNode(hasTestTag("district_opt_sindh_karachi_east"))
        composeRule.onNodeWithTag("district_opt_sindh_karachi_east").performClick()
        composeRule.waitForIdle()
        composeRule.onNodeWithTag("taluka_opt_all").performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.selectedProvinceId == "sindh" &&
                viewModel.uiState.value.selectedDistrictId == "sindh_karachi_east"
        }
        assertTrue(viewModel.uiState.value.filteredWorkers.isNotEmpty())
        assertTrue(
            viewModel.uiState.value.filteredWorkers.all {
                it.worker.provinceId == "sindh" &&
                    it.worker.districtId == "sindh_karachi_east" &&
                    it.worker.categoryKeys.contains("electrician")
            }
        )

        // 4. Clear all filters restores the full worker list
        composeRule.onNodeWithTag("clear_all_filters_btn").performScrollTo().performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.filteredWorkers.size == initialCount &&
                viewModel.uiState.value.selectedCategoryKey == null &&
                viewModel.uiState.value.selectedProvinceId == null
        }
        assertEquals(initialCount, viewModel.uiState.value.filteredWorkers.size)
        composeRule.onNodeWithText("$initialCount workers").assertIsDisplayed()
    }
}
