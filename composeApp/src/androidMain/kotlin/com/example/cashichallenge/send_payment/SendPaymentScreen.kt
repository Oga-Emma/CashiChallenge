package com.example.cashichallenge.send_payment

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cashichallenge.R
import com.example.cashichallenge.coreui.OutlineTextFieldWithState
import com.example.cashichallenge.coreui.TextInputState
import com.example.cashichallenge.domain.UiRequestState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendPaymentScreen(
    modifier: Modifier = Modifier,
    onSendPaymentClick: (SendPaymentFormState) -> Unit,
    onBackClick: () -> Unit,
    state: UiRequestState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.label_transfer))
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
        Column(modifier = modifier.padding(padding)) {
            SendPaymentForm(
                onSendPaymentClick = onSendPaymentClick,
                onCancelClick = onBackClick,
                isLoading = state.loading
            )
        }
    }
}

@Composable
fun SendPaymentForm(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onSendPaymentClick: (SendPaymentFormState) -> Unit,
    onCancelClick: () -> Unit
) {
    val context = LocalContext.current

    var recipientState by remember {
        mutableStateOf(
            TextInputState(
                errorMsg = "Please enter a valid email"
            )
        )
    }
    var amountState by remember {
        mutableStateOf(
            TextInputState(
                errorMsg = "Please enter a valid amount"
            )
        )
    }
    var currencyState by remember {
        mutableStateOf(
            TextInputState(
                errorMsg = "Please select a currency"
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(56.dp))
        OutlineTextFieldWithState(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.label_recipient),
            state = recipientState,
            onValueChange = { recipientState = it }
        )
        OutlineTextFieldWithState(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.label_amount),
            keyboardType = KeyboardType.Number,
            state = amountState,
            onValueChange = { amountState = it }
        )
        OutlineTextFieldWithState(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.label_currency),
            state = currencyState,
            onValueChange = { currencyState = it }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onCancelClick
            ) {
                Text(stringResource(R.string.btn_cancel))
            }
            FilledTonalButton(
                modifier = Modifier.weight(1f),
                onClick = {
                    if (recipientState.value.isBlank() || Patterns.EMAIL_ADDRESS.matcher(
                            recipientState.value
                        ).matches().not()
                    ) {
                        recipientState = recipientState.copy(showError = true)
                    }

                    val amount = amountState.value.toDoubleOrNull()
                    if (amount == null) {
                        amountState = amountState.copy(showError = true)
                    }

                    if (currencyState.value.isBlank()) {
                        currencyState = currencyState.copy(showError = true)
                    }

                    if (recipientState.isError || amountState.isError || currencyState.isError) {
                        Toast.makeText(
                            context,
                            "Please fix the errors",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        onSendPaymentClick(
                            SendPaymentFormState(
                                recipientEmail = recipientState.value,
                                amount = amount!!,
                                currency = currencyState.value
                            )
                        )
                    }
                }
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator(
                            modifier = modifier
                                .size(16.dp)
                                .align(Alignment.Center),
                            strokeWidth = (ProgressIndicatorDefaults.CircularStrokeWidth.value / 2).dp,
                        )
                    }
                } else {
                    Text(stringResource(R.string.btn_send_payment))
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSendPaymentScreen() {
    SendPaymentScreen(
        onSendPaymentClick = {},
        onBackClick = {},
        state = UiRequestState(),
    )
}