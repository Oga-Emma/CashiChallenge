package com.example.cashichallenge.domain.model.dto

data class SendPaymentDto(
    val recipientEmail: String?,
    val amount: Double?,
    val currency: String?
)
