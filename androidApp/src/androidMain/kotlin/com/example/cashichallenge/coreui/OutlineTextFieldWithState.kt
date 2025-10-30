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

@Composable
fun OutlineTextFieldWithState(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    error: String,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
) {
    val hasError = error.isNotBlank()

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        label = {
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
        },
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        singleLine = true,
        keyboardActions = keyboardActions,
        isError = hasError,
        supportingText = {
            if (hasError) {
                Text(
                    text = error, modifier = Modifier.fillMaxWidth(),
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
        value = "",
        error = "",
        onValueChange = {

        }
    )
}

@Preview
@Composable
private fun PreviewOutlineTextFieldWithStateError() {
    OutlineTextFieldWithState(
        label = "Test",
        value = "",
        error = "Error message",
        onValueChange = {

        }
    )
}