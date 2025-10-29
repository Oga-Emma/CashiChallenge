package com.example.cashichallenge.transaction_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cashichallenge.R
import com.example.cashichallenge.core.util.TestTags
import com.example.cashichallenge.coreui.EmptyState
import com.example.cashichallenge.coreui.ErrorState
import com.example.cashichallenge.domain.UiDataState
import com.example.cashichallenge.domain.model.Transaction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    state: UiDataState<List<Transaction>>,
    onRetryClick: () -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.label_transactions))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        },
    ) { padding ->
        Box(
            modifier = modifier.padding(padding)
                .fillMaxSize(),
        ) {

            if (state.error.isNullOrBlank().not()) {
                ErrorState(
                    modifier = modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .padding(horizontal = 36.dp),
                    description = state.error,
                    onRetry = onRetryClick
                )
            } else if (state.loading) {
                CircularProgressIndicator(
                    modifier = modifier
                        .size(16.dp)
                        .align(Alignment.Center)
                        .testTag(TestTags.LOADING_INDICATOR),
                    strokeWidth = (ProgressIndicatorDefaults.CircularStrokeWidth.value / 2).dp,
                )
            } else if (state.data != null && state.data.isNotEmpty()) {
                TransactionsList(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag(TestTags.TRANSACTION_LIST),
                    transactions = state.data,
                    onTransactionClick = {})
            } else {
                EmptyState(
                    modifier = Modifier
                        .align(Alignment.Center),
                    heading = "No Transactions",
                    description = "your transactions history will appear here"
                )
            }
        }
    }
}

@Composable
fun TransactionsList(
    modifier: Modifier = Modifier,
    transactions: List<Transaction>,
    onTransactionClick: (Transaction) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(vertical = 16.dp)
    ) {
        items(transactions) {
            TransactionListItem(
                modifier = Modifier.padding(horizontal = 8.dp),
                transaction = it,
                onClick = onTransactionClick
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun TransactionListItem(
    modifier: Modifier = Modifier,
    transaction: Transaction,
    onClick: (Transaction) -> Unit
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            modifier = Modifier
                .background(
                    color = Color.LightGray.copy(alpha = 0.2f),
                    shape = CircleShape
                )
                .padding(12.dp)
                .testTag(TestTags.TRANSFER_LIST_ITEM_RECIPIENT_EMAIL),
            text = transaction.recipientEmail.subSequence(0, 2).toString().uppercase(),
            style = MaterialTheme.typography.titleSmall
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = transaction.recipientEmail,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Pending",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray
                )
            )
        }
        Text(
            "${transaction.amount} ${transaction.currency}"
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "",
            tint = Color.LightGray
        )
    }
}


@Preview
@Composable
private fun PreviewTransactionHistoryScreen() {
    TransactionHistoryScreen(
        onBackClick = {

        },
        state = UiDataState(
            data = emptyList()
        ),
        onRetryClick = {},
    )
}

@Preview(
    showSystemUi = true
)
@Composable
private fun PreviewTransactionsList() {
    TransactionsList(
        transactions = (1..10).toList().map {
            Transaction(
                senderId = "1234",
                recipientEmail = "test@mail.com",
                amount = 200.0,
                currency = "NGN",
                timestamp = 1761641948
            )
        },
        onTransactionClick = {}
    )
}