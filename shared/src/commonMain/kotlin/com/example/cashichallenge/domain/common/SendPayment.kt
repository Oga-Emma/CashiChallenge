package com.example.cashichallenge.domain.common

data class SendPaymentFormState(
    val recipientError: String = "",
    val amountError: String = "",
    val currencyError: String = "",
) {
    val isValid: Boolean
        get() = recipientError.isEmpty() && amountError.isEmpty() && currencyError.isEmpty()
}
