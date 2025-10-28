package com.example.cashichallenge.domain.usecase

import com.example.cashichallenge.data.local.Cache
import com.example.cashichallenge.data.repository.TransactionDataSource
import com.example.cashichallenge.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

class GetTransactionsUseCase(
    private val transactionDataSource: TransactionDataSource,
    private val cache: Cache
) {
    operator fun invoke(): Flow<List<Transaction>> {
        return transactionDataSource.getTransactions(cache.getUserId())
    }
}
