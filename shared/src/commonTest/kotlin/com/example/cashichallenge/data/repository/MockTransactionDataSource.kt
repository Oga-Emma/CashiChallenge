package com.example.cashichallenge.data.repository

import com.example.cashichallenge.domain.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MockTransactionDataSource : TransactionDataSource {
    private val transactions = mutableListOf<Transaction>()

    override fun getTransactions(userId: String): Flow<List<Transaction>> {
        return flowOf(transactions.filter { it.senderId == userId })
    }

    override suspend fun saveTransaction(data: Transaction) {
        transactions.add(data)
    }
}
