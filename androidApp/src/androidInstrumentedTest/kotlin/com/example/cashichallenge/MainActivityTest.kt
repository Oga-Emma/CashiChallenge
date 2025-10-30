package com.example.cashichallenge

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun whenAppIsLaunched_homeScreenShouldDisplaysCorrectly() {
        composeTestRule.onNodeWithText("Send payment").assertExists()
        composeTestRule.onNodeWithText("View Transactions").assertExists()
    }

    @Test
    fun whenSendPaymentIsPressed_shouldNavigateToPaymentScreen() {
        composeTestRule.onNodeWithText("Send payment").performClick()
        composeTestRule.onNodeWithText("Transfer Money").assertExists()
    }

    @Test
    fun whenViewTransactionIsPressed_shouldNavigateToTransactionScreen() {
        composeTestRule.onNodeWithText("View Transactions").performClick()
        composeTestRule.onNodeWithText("Transactions").assertExists()
    }
}
