package com.example.cashichallenge.business

import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.model.TransactionStatus
import com.example.cashichallenge.domain.model.request.InitiatePaymentRequest
import com.example.cashichallenge.repository.TransactionRepository
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class TransactionBusiness(val transactionRepository: TransactionRepository) {
    @OptIn(ExperimentalTime::class)
    suspend fun initiatePayment(request: InitiatePaymentRequest): Transaction {
        val newTransaction = Transaction(
            senderId = request.senderId,
            recipientEmail = request.recipientEmail,
            amount = request.amount,
            currency = request.currency,
            timestamp = Clock.System.now().toEpochMilliseconds(),
            status = TransactionStatus.PENDING
        )

        return transactionRepository.saveTransaction(newTransaction)
    }
}
