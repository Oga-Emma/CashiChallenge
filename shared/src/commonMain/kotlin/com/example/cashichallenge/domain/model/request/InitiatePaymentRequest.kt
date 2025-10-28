package com.example.cashichallenge.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class InitiatePaymentRequest(
    val senderId: String,
    val recipientEmail: String,
    val amount: Double,
    val currency: String,
)
