package com.example.cashichallenge.domain.usecase

import com.example.cashichallenge.data.local.Cache
import com.example.cashichallenge.data.local.MockCache
import com.example.cashichallenge.data.repository.MockTransactionDataSource
import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.model.request.Currency
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetTransactionsUseCaseTest {

    private lateinit var transactionDataSource: MockTransactionDataSource
    private lateinit var cache: Cache
    private lateinit var getTransactionsUseCase: GetTransactionsUseCase

    @BeforeTest
    fun setUp() {
        transactionDataSource = MockTransactionDataSource()
        cache = MockCache()

        getTransactionsUseCase = GetTransactionsUseCase(transactionDataSource, cache)
    }

    @Test
    fun `invoke should return flow of transactions from data source`() = runTest {
        // Given
        val transaction = Transaction(
            senderId = cache.getUserId(),
            recipientEmail = "test@mail.com",
            amount = 200.0,
            currency = Currency.NGN,
            timestamp = 1761641948
        )

        transactionDataSource.saveTransaction(transaction)

        // When
        val result = getTransactionsUseCase()

        // Then
        assertEquals(listOf(transaction), result.first())
    }
}
