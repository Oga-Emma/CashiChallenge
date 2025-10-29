package com.example.cashichallenge.repository

import com.example.cashichallenge.domain.model.Transaction

interface TransactionRepository {
    suspend fun saveTransaction(transaction: Transaction): Transaction
}

class FirebaseTransactionRepository(/*private val firestore: Firestore*/) :
    TransactionRepository {

    override suspend fun saveTransaction(transaction: Transaction): Transaction {
        /*val res = firestore.collection(Constants.FIRESTORE_TRANSACTIONS)
            .document(transaction.id)
            .set(transaction)*/

        return transaction
    }
}
