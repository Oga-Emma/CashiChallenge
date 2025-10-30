package com.example.cashichallenge.domain.usecase

import com.example.cashichallenge.domain.common.SendPaymentFormState
import com.example.cashichallenge.domain.model.dto.SendPaymentDto

class ValidatePaymentFormUseCase {
    operator fun invoke(sendPaymentDto: SendPaymentDto): SendPaymentFormState {
        return SendPaymentFormState(
            recipientError = if (validateEmail(sendPaymentDto.recipientEmail).not()) "Please enter a valid email address." else "",
            amountError = if (validateAmount(sendPaymentDto.amount).not()) "Amount must be greater than zero." else "",
            currencyError = if (validateCurrency(sendPaymentDto.currency).not()) "Please select a currency." else ""
        )
    }

    val EMAIL_REGEX = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    private fun validateEmail(email: String?): Boolean {
        return email.isNullOrBlank().not() && EMAIL_REGEX.matches(email)
    }

    private fun validateAmount(amount: Double?): Boolean {
        return amount != null && amount > 0
    }

    private fun validateCurrency(currency: String?): Boolean {
        return currency.isNullOrBlank().not()
    }
}
