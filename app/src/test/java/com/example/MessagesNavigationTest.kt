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
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performScrollToNode
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.KaamDatabase
import com.example.data.local.SampleSeedData
import com.example.data.prefs.KaamPrefsStore
import com.example.i18n.AppLanguage
import com.example.i18n.stringsFor
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
class MessagesNavigationTest {

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
    fun `bottom navigation displays 5 tabs with Messages at end and unread badge and opens inbox`() {
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

        // Wait until seeded conversations load (3 unread total across demo conversations)
        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.conversations.isNotEmpty()
        }

        // Verify all 5 bottom navigation tabs exist in English
        composeRule.onNodeWithTag("nav_home").assertIsDisplayed()
        composeRule.onNodeWithTag("nav_requests").assertIsDisplayed()
        composeRule.onNodeWithTag("nav_profile").assertIsDisplayed()
        composeRule.onNodeWithTag("nav_admin").assertIsDisplayed()
        composeRule.onNodeWithTag("nav_messages").assertIsDisplayed()
        composeRule.onNodeWithTag("messages_unread_badge", useUnmergedTree = true).assertIsDisplayed()
        composeRule.onNodeWithText("3", useUnmergedTree = true).assertIsDisplayed()

        // Switch to Sindhi (RTL) via UI selector and verify all 5 Sindhi labels render cleanly
        composeRule.onNodeWithTag("lang_chip_sd").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.preferences.language == AppLanguage.SINDHI
        }
        val sd = stringsFor(AppLanguage.SINDHI)
        composeRule.onNodeWithText(sd.navHome).assertIsDisplayed()
        composeRule.onNodeWithText(sd.navRequests).assertIsDisplayed()
        composeRule.onNodeWithText(sd.navProfile).assertIsDisplayed()
        composeRule.onNodeWithText(sd.navAdmin).assertIsDisplayed()
        composeRule.onNodeWithText(sd.navMessages).assertIsDisplayed()

        // Switch to Urdu (RTL) via UI selector and verify Urdu Messages tab label
        composeRule.onNodeWithTag("lang_chip_ur").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.preferences.language == AppLanguage.URDU
        }
        val ur = stringsFor(AppLanguage.URDU)
        composeRule.onNodeWithText(ur.navMessages).assertIsDisplayed()

        // Back to English and tap the 5th Messages tab
        composeRule.onNodeWithTag("lang_chip_en").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.preferences.language == AppLanguage.ENGLISH
        }

        composeRule.onNodeWithTag("nav_messages").performClick()
        composeRule.waitForIdle()

        // Inbox displays active customer-worker conversations with shared address & GPS indicators
        composeRule.onNodeWithTag("messages_inbox_list").performScrollToNode(hasTestTag("conv_card_conv_demo_1"))
        composeRule.onNodeWithTag("conv_card_conv_demo_1").assertIsDisplayed()
        composeRule.onNodeWithTag("messages_inbox_list").performScrollToNode(hasTestTag("conv_card_conv_demo_2"))
        composeRule.onNodeWithTag("conv_card_conv_demo_2").assertIsDisplayed()
        composeRule.onNodeWithTag("messages_inbox_list").performScrollToNode(hasTestTag("conv_card_conv_demo_3"))
        composeRule.onNodeWithTag("conv_card_conv_demo_3").assertIsDisplayed()

        // Open first conversation -> marks unread (2) as read (unread drops from 3 to 1) and renders address & GPS pin cards
        composeRule.onNodeWithTag("messages_inbox_list").performScrollToNode(hasTestTag("conv_card_conv_demo_1"))
        composeRule.onNodeWithTag("conv_card_conv_demo_1").performClick()
        composeRule.waitUntil(timeoutMillis = 5_000) {
            Shadows.shadowOf(Looper.getMainLooper()).idle()
            viewModel.uiState.value.unreadMessagesCount == 1
        }
        composeRule.onNodeWithTag("chat_messages_list").performScrollToIndex(1)
        composeRule.onNodeWithTag("chat_address_card_msg_demo_1_2").assertIsDisplayed()
        composeRule.onNodeWithTag("chat_messages_list").performScrollToIndex(2)
        composeRule.onNodeWithTag("chat_gps_card_msg_demo_1_3").assertIsDisplayed()
        composeRule.onNodeWithTag("open_maps_pin_btn_msg_demo_1_3").performScrollTo()
        composeRule.onNodeWithTag("open_maps_pin_btn_msg_demo_1_3").assertIsDisplayed()

        assertEquals(3, SampleSeedData.demoConversations.sumOf { it.unreadCount })
    }
}
