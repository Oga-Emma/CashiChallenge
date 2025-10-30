package com.example.cashichallenge.send_payment

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.cashichallenge.MainActivity
import org.junit.Rule
import org.junit.Test

class SendPaymentScreenTest  {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun whenSendPaymentScreenIsLoaded_shouldShowAllComponents() {
        // Given
        composeTestRule.onNodeWithText("Send payment").performClick()

        // Then
        composeTestRule.onNodeWithText("Recipient Email").assertExists()
        composeTestRule.onNodeWithText("Amount").assertExists()
        composeTestRule.onNodeWithText("Currency").assertExists()
        composeTestRule.onNode(hasText("Send payment"), useUnmergedTree = true).assertExists()
        composeTestRule.onNodeWithText("Cancel").assertExists()
    }

    @Test
    fun whenInvalidRecipientEmailAndAmountIsProvided_sendPaymentShouldShowError() {
        // Given
        composeTestRule.onNodeWithText("Send payment").performClick()

        // Verify initial state
        composeTestRule.onNodeWithText("Please enter a valid email address.").assertDoesNotExist()
        composeTestRule.onNodeWithText("Amount must be greater than zero.").assertDoesNotExist()

        composeTestRule.onNodeWithText("Recipient Email").performTextInput("invalidemail")
        composeTestRule.onNodeWithText("Amount").performTextInput("0")
        composeTestRule.onNodeWithText("Currency").performTextInput("NGN")

        // When
        composeTestRule.onNode(hasText("Send payment"), useUnmergedTree = true).performClick()

        // Then
        composeTestRule.onNodeWithText("Please enter a valid email address.").assertExists()
        composeTestRule.onNodeWithText("Amount must be greater than zero.").assertExists()
    }

    @Test
    fun whenValidInputIsProvided_sendPaymentShouldNotShowError() {
        // Given
        composeTestRule.onNodeWithText("Send payment").performClick()

        composeTestRule.onNodeWithText("Recipient Email").performTextInput("test@email.com")
        composeTestRule.onNodeWithText("Amount").performTextInput("100")
        composeTestRule.onNodeWithText("Currency").performTextInput("NGN")

        // When
        composeTestRule.onNode(hasText("Send payment"), useUnmergedTree = true).performClick()

        // Then
        composeTestRule.onNodeWithText("Please enter a valid email address.").assertDoesNotExist()
        composeTestRule.onNodeWithText("Amount must be greater than zero.").assertDoesNotExist()
    }
}
