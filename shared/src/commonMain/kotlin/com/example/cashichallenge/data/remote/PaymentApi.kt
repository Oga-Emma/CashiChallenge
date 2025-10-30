package com.example.cashichallenge.data.remote

import com.example.cashichallenge.domain.model.Transaction
import com.example.cashichallenge.domain.model.reponse.ApiResponse
import com.example.cashichallenge.domain.model.request.InitiatePaymentRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface PaymentApi {
    suspend fun sendPayment(request: InitiatePaymentRequest): ApiResponse<Transaction?>
}

class PaymentApiImpl(
    val client: HttpClient
) : PaymentApi {
    override suspend fun sendPayment(request: InitiatePaymentRequest): ApiResponse<Transaction?> {
        val response: HttpResponse = client.post("/payments") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        return try {
            response.body()
        } catch (e: Exception){
            ApiResponse(
                success = false,
                message = "Unknow error"
            )
        }
    }
}
