package com.example.cashichallenge.transaction_history

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.cashichallenge.MainActivity
import com.example.cashichallenge.core.util.TestTags
import com.example.cashichallenge.core.model.UiDataState
import com.example.cashichallenge.domain.model.Transaction
import org.junit.Rule
import org.junit.Test

class TransactionHistoryScreenTest {

    @get:Rule
    val rule = createComposeRule()

    val loadingIndicator = hasTestTag(TestTags.LOADING_INDICATOR)
    val emptyState = hasText("No Transactions")
    val errorState = hasText("Error occurred")
    val transactionsList = hasTestTag(TestTags.TRANSACTION_LIST)

    @Test
    fun whenStateIsLoading_showsLoadingIndicator() {
        // Given
        val state = UiDataState<List<Transaction>>(
            loading = true,
            error = null,
            data = null
        )

        // When
        rule.setContent {
            TransactionHistoryScreen(
                onBackClick = {},
                state = state,
                onRetryClick = {},
            )
        }

        // Then
        rule.onNode(loadingIndicator).assertExists()

        rule.onNode(emptyState).assertDoesNotExist()
        rule.onNode(transactionsList).assertDoesNotExist()
        rule.onNode(errorState).assertDoesNotExist()
    }

    @Test
    fun whenStateHasError_showsErrorState() {
        // Given
        val state = UiDataState<List<Transaction>>(
            loading = true,
            error = "Some error occurred",
            data = null
        )

        // When
        rule.setContent {
            TransactionHistoryScreen(
                onBackClick = {},
                state = state,
                onRetryClick = {},
            )
        }

        // Then
        rule.onNode(hasText("Some error occurred")).assertExists()

        rule.onNode(emptyState).assertDoesNotExist()
        rule.onNode(loadingIndicator).assertDoesNotExist()
        rule.onNode(transactionsList).assertDoesNotExist()
    }

    @Test
    fun whenStateDataIsEmpty_showsEmptyState() {
        // Given
        val state = UiDataState(
            loading = false,
            error = null,
            data = listOf<Transaction>()
        )

        // When
        rule.setContent {
            TransactionHistoryScreen(
                onBackClick = {},
                state = state,
                onRetryClick = {},
            )
        }

        // Then
        rule.onNode(emptyState).assertExists()

        rule.onNode(loadingIndicator).assertDoesNotExist()
        rule.onNode(transactionsList).assertDoesNotExist()
        rule.onNode(errorState).assertDoesNotExist()
    }

    @Test
    fun whenStateHasData_showsTransactionList() {
        // Given
        val state = UiDataState(
            loading = false,
            error = null,
            data = listOf(
                Transaction(
                    senderId = "1123",
                    recipientEmail = "test@mail.com",
                    amount = 200.0,
                    currency = "NGN",
                    timestamp = 1761641948
                )
            )
        )

        // When
        rule.setContent {
            TransactionHistoryScreen(
                onBackClick = {},
                state = state,
                onRetryClick = {},
            )
        }

        // Then
        rule.onNode(transactionsList).assertExists()

        rule.onNode(loadingIndicator).assertDoesNotExist()
        rule.onNode(emptyState).assertDoesNotExist()
        rule.onNode(errorState).assertDoesNotExist()
    }
}
