package com.example.cashichallenge.domain.usecase

import com.example.cashichallenge.data.local.Cache
import com.example.cashichallenge.data.mapper.SendPaymentRequestMapper
import com.example.cashichallenge.data.remote.PaymentRepository
import com.example.cashichallenge.domain.common.Resource
import com.example.cashichallenge.domain.model.ErrorCodes
import com.example.cashichallenge.domain.model.ErrorResponse
import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.model.dto.SendPaymentDto
import com.example.cashichallenge.domain.model.request.Currency
import com.example.cashichallenge.domain.model.request.InitiatePaymentRequest
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import net.orandja.either.Either
import net.orandja.either.Left
import net.orandja.either.Right
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SendPaymentUseCaseTest {

    private lateinit var paymentRepository: PaymentRepository
    private lateinit var cache: Cache
    private lateinit var sendPaymentRequestMapper: SendPaymentRequestMapper
    private lateinit var sendPaymentUseCase: SendPaymentUseCase

    @BeforeTest
    fun setUp() {
        paymentRepository = mock<PaymentRepository>()
        cache = mock<Cache>()
        sendPaymentRequestMapper = SendPaymentRequestMapper()
        sendPaymentUseCase = SendPaymentUseCase(paymentRepository, cache, sendPaymentRequestMapper)
    }

    @Test
    fun `invoke returns success when payment is successful`() = runTest {
        // Given
        val sendPaymentDto = SendPaymentDto("test@test.com", 100.0, Currency.NGN)
        val userId = "user123"
        val transaction = Transaction()
        val successResult: Either<ErrorResponse, Transaction> = Right(transaction)

        every { cache.getUserId() } returns userId
        everySuspend { paymentRepository.initiatePayment(any()) } returns successResult

        // When
        val emissions = sendPaymentUseCase(sendPaymentDto).toList()

        // Then
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Success)
        assertEquals(transaction, (emissions[1] as Resource.Success).data)
    }

   @Test
    fun `invoke returns error when payment fails`() = runTest {
        // Given
        val sendPaymentDto = SendPaymentDto("test@test.com", 100.0, Currency.NGN)
        val userId = "user123"
        val appError = ErrorResponse(message = "Insufficient funds", code = ErrorCodes.UNKNOWN_ERROR)
        val errorResult: Either<ErrorResponse, Transaction> = Left(appError)

        every { cache.getUserId() } returns userId
        everySuspend { paymentRepository.initiatePayment(any()) } returns errorResult

        // When
        val emissions = sendPaymentUseCase(sendPaymentDto).toList()

        // Then
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue(emissions[1] is Resource.Error)
        assertEquals("Insufficient funds", (emissions[1] as Resource.Error).message)
    }
}
