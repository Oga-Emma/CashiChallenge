/*
package com.example.cashichallenge.transaction_history

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.cashichallenge.domain.UiDataState
import org.junit.Rule
import org.junit.Test

class TransactionHistoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun whenStateIsEmpty_showsEmptyState() {
        composeTestRule.setContent {
            TransactionHistoryScreen(
                onBackClick = {},
                state = UiDataState(
                    data = emptyList()
                ),
                onRetryClick = {},
            )
        }

        composeTestRule.onNodeWithText("No Transactions").assertExists()
    }
}
*/
