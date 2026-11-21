package com.example

import android.app.Application
import android.os.Looper
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.KaamDatabase
import com.example.data.prefs.KaamPrefsStore
import com.example.ui.KaamPakistanApp
import com.example.ui.KaamViewModel
import com.example.ui.LocationViewModel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class SplashAndBrandTest {

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
    fun `splash screen displays full emblem and progress indicator and transitions to branded home screen after 2_5s`() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = KaamViewModel(app)
        val locationViewModel = LocationViewModel()

        composeRule.setContent {
            KaamPakistanApp(
                viewModel = viewModel,
                locationViewModel = locationViewModel
            )
        }

        // Immediately upon launch, Splash screen with emblem & loading indicator is shown
        composeRule.onNodeWithTag("splash_screen").assertIsDisplayed()
        composeRule.onNodeWithTag("splash_emblem").assertIsDisplayed()
        composeRule.onNodeWithTag("splash_loading_indicator").assertIsDisplayed()

        // Advance Compose clock cleanly past the 2500ms splash hold + 450ms crossfade
        composeRule.mainClock.advanceTimeBy(3_100L)
        composeRule.waitUntil(timeoutMillis = 5_000L) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            true
        }
        composeRule.waitForIdle()

        // Main Home Screen appears with circular brand badge next to "ڪام پاڪستان | KAAM PAKISTAN"
        composeRule.onNodeWithTag("home_brand_header").assertIsDisplayed()
        composeRule.onNodeWithTag("home_brand_badge").assertIsDisplayed()
        composeRule.onNodeWithTag("home_brand_title").assertIsDisplayed()
        composeRule.onNodeWithText("ڪام پاڪستان | KAAM PAKISTAN").assertIsDisplayed()
        composeRule.onNodeWithTag("nav_home").assertIsDisplayed()
    }
}
