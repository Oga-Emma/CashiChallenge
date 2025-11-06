package com.example.cashichallenge.domain.model.dto

import com.example.cashichallenge.domain.model.request.Currency

data class SendPaymentDto(
    val recipientEmail: String?,
    val amount: Double?,
    val currency: Currency?
)
