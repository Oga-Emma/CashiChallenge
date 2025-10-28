package com.example.cashichallenge.repository

import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.util.Constants
import com.google.cloud.firestore.Firestore

interface TransactionRepository {
    suspend fun saveTransaction(transaction: Transaction): Transaction
}

class FirebaseTransactionRepository(private val firestore: Firestore) :
    TransactionRepository {

    override suspend fun saveTransaction(transaction: Transaction): Transaction {
        val res = firestore.collection(Constants.FIRESTORE_TRANSACTIONS)
            .add(transaction)

        return res.get().get().get().toObject(Transaction::class.java)
            ?: transaction
    }
}
