package com.example.cashichallenge.domain.model

import kotlin.uuid.ExperimentalUuidApi
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Serializable
data class Transaction (
    val id: String = Uuid.random().toString(),
    val senderId: String = "",
    val recipientEmail: String = "",
    val amount: Double = 0.0,
    val currency: String = "",
    val timestamp: Long = 0L,
    val status: TransactionStatus = TransactionStatus.PENDING
)

enum class TransactionStatus {
    PENDING, COMPLETED, CANCELED, FAILED
}
