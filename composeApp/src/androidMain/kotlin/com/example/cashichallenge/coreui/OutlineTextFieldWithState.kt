package com.example.cashichallenge.coreui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

data class TextInputState(
    val value: String = "",
    val errorMsg: String = "",
    val showError: Boolean = false
) {
    val isError: Boolean
        get() = showError && errorMsg.isNotBlank()
}

@Composable
fun OutlineTextFieldWithState(
    modifier: Modifier = Modifier,
    label: String,
    state: TextInputState,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (TextInputState) -> Unit,
) {

    OutlinedTextField(
        modifier = modifier,
        value = state.value,
        onValueChange = {
            onValueChange(
                state.copy(
                    value = it,
                    showError = false,
                )
            )
        },
        label = {
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true,
        keyboardActions = keyboardActions,
        isError = state.isError,
        supportingText = {
            if (state.isError) {
                Text(
                    text = state.errorMsg, modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
    )
}

@Preview
@Composable
private fun PreviewOutlineTextFieldWithStateNormal() {
    OutlineTextFieldWithState(
        label = "Test",
        state = TextInputState(),
        onValueChange = {

        }
    )
}

@Preview
@Composable
private fun PreviewOutlineTextFieldWithStateError() {
    OutlineTextFieldWithState(
        label = "Test",
        state = TextInputState(
            showError = true,
            errorMsg = "Error message"
        ),
        onValueChange = {

        }
    )
}