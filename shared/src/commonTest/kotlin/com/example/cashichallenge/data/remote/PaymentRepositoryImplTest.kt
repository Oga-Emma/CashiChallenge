package com.example.cashichallenge.data.remote

import com.example.cashichallenge.data.repository.TransactionDataSource
import com.example.cashichallenge.domain.model.ErrorCodes
import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.model.reponse.ApiResponse
import com.example.cashichallenge.domain.model.request.Currency
import com.example.cashichallenge.domain.model.request.InitiatePaymentRequest
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify.VerifyMode.Companion.exactly
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PaymentRepositoryImplTest {

    private lateinit var paymentApi: PaymentApi
    private lateinit var transactionDataSource: TransactionDataSource
    private lateinit var paymentRepository: PaymentRepositoryImpl

    @BeforeTest
    fun setUp() {
        paymentApi = mock<PaymentApi>()
        transactionDataSource = mock<TransactionDataSource>()
        paymentRepository = PaymentRepositoryImpl(paymentApi, transactionDataSource)
    }

    @Test
    fun `initiatePayment returns Right and saves transaction on API success`() = runTest {
        // Given
        val request = InitiatePaymentRequest("sender1", "rec@mail.com", 100.0, Currency.NGN)
        val transaction = Transaction()
        val successResponse = ApiResponse<Transaction?>(success = true, data = transaction, message = "Success")

        everySuspend { paymentApi.sendPayment(request) } returns successResponse
        everySuspend { transactionDataSource.saveTransaction(transaction) } returns Unit

//        coEvery { paymentApi.sendPayment(request) } returns successResponse

        // When
        val result = paymentRepository.initiatePayment(request)

        // Then
        assertNotNull(result.rightOrNull)
        assertEquals(transaction, result.right)

        verifySuspend(exactly(1)) { paymentApi.sendPayment(request) }
        verifySuspend(exactly(1)) { transactionDataSource.saveTransaction(transaction) }
    }

     @Test
     fun `initiatePayment returns Left on API failure`() = runTest {
         // Given
         val request = InitiatePaymentRequest("sender1", "rec@mail.com", 100.0, Currency.NGN)
         val errorResponse =
             ApiResponse<Transaction?>(success = false, message = "Some server error")

         everySuspend { paymentApi.sendPayment(request) } returns errorResponse

         // When
         val result = paymentRepository.initiatePayment(request)

         // Then
         assertNotNull(result.leftOrNull)
         assertEquals("Some server error", result.left.message)
         assertEquals(ErrorCodes.SERVER_ERROR, result.left.code)

         verifySuspend(exactly(1)) { paymentApi.sendPayment(request) }
         verifySuspend(exactly(0)) { transactionDataSource.saveTransaction(any()) }
     }

    @Test
     fun `initiatePayment returns Left when API throws exception`() = runTest {
         // Given
         val request = InitiatePaymentRequest("sender1", "rec@mail.com", 100.0, Currency.NGN)
         val exception = RuntimeException("Network error")

        everySuspend { paymentApi.sendPayment(request) } throws exception

         // When
         val result = paymentRepository.initiatePayment(request)

         // Then
        assertNotNull(result.leftOrNull)
        assertEquals("Network error", result.left.message)
        assertEquals(ErrorCodes.UNKNOWN_ERROR, result.left.code)

        verifySuspend(exactly(1)) { paymentApi.sendPayment(request) }
        verifySuspend(exactly(0)) { transactionDataSource.saveTransaction(any()) }
     }
}
