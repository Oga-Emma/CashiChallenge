@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cashichallenge.send_payment

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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cashichallenge.R
import com.example.cashichallenge.core.util.TestTags
import com.example.cashichallenge.coreui.OutlineTextFieldWithState
import com.example.cashichallenge.domain.model.dto.SendPaymentDto
import com.example.cashichallenge.domain.model.request.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendPaymentScreen(
    modifier: Modifier = Modifier,
    onSendPaymentClick: (SendPaymentDto) -> Unit,
    onBackClick: () -> Unit,
    state: SendPaymentScreenState
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
                state = state,
                onSendPaymentClick = onSendPaymentClick,
                onCancelClick = onBackClick,
            )
        }
    }
}

@Composable
fun SendPaymentForm(
    modifier: Modifier = Modifier,
    state: SendPaymentScreenState,
    onSendPaymentClick: (SendPaymentDto) -> Unit,
    onCancelClick: () -> Unit,
) {
    var state by remember(state) { mutableStateOf(state) }
    var recipientState by remember { mutableStateOf("") }
    var amountState by remember { mutableStateOf("") }

    val options = Currency.entries
    var expanded by remember { mutableStateOf(false) }
    var currencyState by remember { mutableStateOf(options[0]) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(56.dp))
        OutlineTextFieldWithState(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.label_recipient),
            error = state.formState.recipientError,
            value = recipientState,
            onValueChange = {
                recipientState = it
                state = state.copy(formState = state.formState.copy(recipientError = ""))
            }
        )
        OutlineTextFieldWithState(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.label_amount),
            keyboardType = KeyboardType.Number,
            error = state.formState.amountError,
            value = amountState,
            onValueChange = {
                amountState = it
                state = state.copy(formState = state.formState.copy(amountError = ""))
            }
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlineTextFieldWithState(
                modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true).fillMaxWidth(),
                label = stringResource(R.string.label_currency),
                error = state.formState.currencyError,
                value = currencyState.name,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                onValueChange = {
                    state = state.copy(formState = state.formState.copy(currencyError = ""))
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        modifier = Modifier.testTag("${TestTags.DROP_DOWN_ITEM}-$selectionOption"),
                        onClick = {
                            currencyState = selectionOption
                            expanded = false
                        },
                        text = {
                            Text(text = selectionOption.name)
                        }
                    )
                }
            }
        }
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
                    onSendPaymentClick(
                        SendPaymentDto(
                            recipientEmail = recipientState,
                            amount = amountState.toDoubleOrNull(),
                            currency = currencyState
                        )
                    )
                }
            ) {
                if (state.isLoading) {
                    Box(modifier = Modifier.fillMaxWidth()) {
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
        state = SendPaymentScreenState(),
    )
}
