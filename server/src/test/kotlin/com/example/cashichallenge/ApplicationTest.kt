package com.example.cashichallenge

import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.model.reponse.ApiResponse
import com.example.cashichallenge.domain.model.request.Currency
import com.example.cashichallenge.domain.model.request.InitiatePaymentRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun `when amount is invalid, should return bad request`() = testApplication {
        application {
            module() // A reference to your Application.module()
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/payments") {
            contentType(ContentType.Application.Json)
            setBody(InitiatePaymentRequest(
                senderId = "123",
                recipientEmail = "test@example.com",
                amount = -100.0, // Invalid amount
                currency = Currency.NGN
            ))
        }

        assertEquals(HttpStatusCode.BadRequest, response.status)

        val apiResponse = Json.decodeFromString<ApiResponse<Unit>>(response.bodyAsText())

        assertEquals(false, apiResponse.success)
        assertEquals("Invalid amount. Payment amount must be positive.", apiResponse.message)
    }

    @Test
    fun `when payment is valid, should return created`() = testApplication {
        application {
            module()
        }

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/payments") {
            contentType(ContentType.Application.Json)
            setBody(InitiatePaymentRequest(
                senderId = "123",
                recipientEmail = "test@example.com",
                amount = 100.0,
                currency = Currency.NGN
            ))
        }

        assertEquals(HttpStatusCode.Created, response.status)

        val apiResponse = Json.decodeFromString<ApiResponse<Transaction>>(response.bodyAsText())

        assertEquals(true, apiResponse.success)
        assertEquals("Payment request received and transaction initiated.", apiResponse.message)
    }
}
