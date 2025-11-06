package com.example.cashichallenge.data.remote

import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.model.TransactionStatus
import com.example.cashichallenge.domain.model.reponse.ApiResponse
import com.example.cashichallenge.domain.model.request.Currency
import com.example.cashichallenge.domain.model.request.InitiatePaymentRequest
import com.example.cashichallenge.factory.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class PaymentApiImplTest {

    lateinit var client: HttpClient
    lateinit var paymentApi: PaymentApi
    lateinit var responseData: String

    @BeforeTest
    fun setup() {
        val mockEngine = MockEngine { request ->
            respond(
                content = responseData,
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }

        client = HttpClientFactory.create(mockEngine)
        paymentApi = PaymentApiImpl(client)
    }

    @Test
    fun `sendPayment returns success response on valid json`() = runTest {
        // Given
        val successfulResponse = ApiResponse<Transaction?>(
            success = true,
            message = "Payment request received and transaction initiated.",
            data = Transaction(
                id = "8a05a3d5-c73d-4b1f-8e79-f674828606e1",
                amount = 100.0,
                senderId = "jane.doe@example.com",
                currency = Currency.USD,
                timestamp = 1761653484001,
                status = TransactionStatus.PENDING
            )
        )

        responseData = Json.encodeToString(successfulResponse)

        val request = InitiatePaymentRequest(
            senderId = "user1",
            recipientEmail = "test@test.com",
            amount = 100.0,
            currency = Currency.NGN
        )

        // When
        val result = paymentApi.sendPayment(request)

        // Then
        assertEquals(successfulResponse, result)
    }

    @Test
    fun `sendPayment returns error on invalid json`() = runTest {
        // Given
        val errorResponse = ApiResponse<Transaction?>(
            success = false,
            message = "Invalid amount. Payment amount must be positive.",
        )

        responseData = Json.encodeToString(errorResponse)

        val request = InitiatePaymentRequest(
            senderId = "user1",
            recipientEmail = "test@test.com",
            amount = 100.0,
            currency = Currency.NGN
        )

        // When
        val result = paymentApi.sendPayment(request)

        // Then
        assertFalse(result.success)
        assertEquals(errorResponse.message, result.message)
    }
}
