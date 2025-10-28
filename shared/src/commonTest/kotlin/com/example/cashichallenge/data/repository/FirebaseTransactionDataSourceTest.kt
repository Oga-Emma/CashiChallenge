/*
package com.example.cashichallenge.data.repository

import com.example.cashichallenge.domain.model.Transaction
import dev.gitlive.firebase.firestore.CollectionReference
import dev.gitlive.firebase.firestore.DocumentSnapshot
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.Query
import dev.gitlive.firebase.firestore.QuerySnapshot
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class FirebaseTransactionDataSourceTest : BehaviorSpec({

    Given("a FirebaseTransactionDataSource") {
        val firestore: FirebaseFirestore = mockk()
        val transactionDataSource = FirebaseTransactionDataSource(firestore)

        When("getTransactions is called for a user") {
            val userId = "testUser"
            val collectionReference: CollectionReference = mockk()
            val query: Query = mockk()
            val querySnapshot: QuerySnapshot = mockk()
            val documentSnapshot: DocumentSnapshot = mockk()

            beforeTest {
                every { firestore.collection(any()) } returns collectionReference
                every { collectionReference.where(any<String>(), any()) } returns query
                every { query.snapshots } returns flowOf(querySnapshot)
                every { querySnapshot.documents } returns listOf(documentSnapshot)
            }

            Then("it should return a flow of transactions") {
                val expectedTransaction = Transaction("1", 100.0, "Groceries", userId)
                every { documentSnapshot.data<Transaction>() } returns expectedTransaction

                val result = transactionDataSource.getTransactions(userId).first()

                result shouldBe listOf(expectedTransaction)
            }
        }
    }
})
*/
