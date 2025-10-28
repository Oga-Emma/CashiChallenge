package com.example.cashichallenge.domain.usecase

import com.example.cashichallenge.data.repository.TransactionDataSource
import com.example.cashichallenge.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

class GetTransactionsUseCase(private val transactionDataSource: TransactionDataSource) {
    operator fun invoke(userId: String): Flow<List<Transaction>> {
        return transactionDataSource.getTransactions(userId)
    }
}
