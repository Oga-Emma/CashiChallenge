package com.example.cashichallenge.domain.usecase

import com.example.cashichallenge.data.repository.MockTransactionDataSource
import com.example.cashichallenge.domain.model.Transaction
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetTransactionsUseCaseTest {

    private lateinit var transactionDataSource: MockTransactionDataSource
    private lateinit var getTransactionsUseCase: GetTransactionsUseCase

    @BeforeTest
    fun setUp() {
        transactionDataSource = MockTransactionDataSource()
        getTransactionsUseCase = GetTransactionsUseCase(transactionDataSource)
    }

    @Test
    fun `invoke should return flow of transactions from data source`() = runTest {
        // Given
        val transaction = Transaction(
            senderId = "1123",
            recipientEmail = "test@mail.com",
            amount = 200.0,
            currency = "NGN",
            timestamp = 1761641948
        )

        transactionDataSource.addTransaction(transaction)

        // When
        val result = getTransactionsUseCase(transaction.senderId)

        // Then
        assertEquals(listOf(transaction), result.first())
    }
}
