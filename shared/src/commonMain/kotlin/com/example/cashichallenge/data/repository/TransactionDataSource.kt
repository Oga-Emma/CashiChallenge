package com.example.cashichallenge.data.repository

import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.util.Constants
import dev.gitlive.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface TransactionDataSource {
    fun getTransactions(userId: String): Flow<List<Transaction>>
    suspend fun saveTransaction(data: Transaction)
}

class FirebaseTransactionDataSource(val db: FirebaseFirestore) : TransactionDataSource {
    override fun getTransactions(userId: String): Flow<List<Transaction>> {
        return db.collection(Constants.FIRESTORE_TRANSACTIONS)
            .where { Constants.SENDER_ID equalTo userId }
            .snapshots.map { querySnapshot ->
                querySnapshot.documents.map { document -> document.data<Transaction>() }
            }
    }

    override suspend fun saveTransaction(data: Transaction) {
        db.collection(Constants.FIRESTORE_TRANSACTIONS).document(data.id).set(data)
    }
}
