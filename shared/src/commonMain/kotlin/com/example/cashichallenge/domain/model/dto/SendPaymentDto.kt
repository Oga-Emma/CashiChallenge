package com.example.cashichallenge.domain.model.dto

import com.example.cashichallenge.domain.model.request.InitiatePaymentRequest

data class SendPaymentDto(
    val recipientEmail: String,
    val amount: Double,
    val currency: String
)

fun SendPaymentDto.toRequest(userId: String) = InitiatePaymentRequest(
    senderId = userId,
    recipientEmail = recipientEmail,
    amount = amount,
    currency = currency
)
