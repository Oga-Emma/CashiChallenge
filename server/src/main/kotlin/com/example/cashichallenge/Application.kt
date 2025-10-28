package com.example.cashichallenge

import com.example.cashichallenge.business.TransactionBusiness
import com.example.cashichallenge.domain.model.reponse.ApiResponse
import com.example.cashichallenge.domain.model.request.InitiatePaymentRequest
import com.example.cashichallenge.domain.util.Constants
import com.example.cashichallenge.repository.FirebaseTransactionRepository
import com.example.cashichallenge.repository.TransactionRepository
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun main() {
    embeddedServer(
        Netty,
        port = Constants.SERVER_PORT,
        host = "0.0.0.0",
        module = Application::module
    )
        .start(wait = true)
}

fun Application.module() {
    initializeFirebase()
    val transactionRepository: TransactionRepository = FirebaseTransactionRepository(
        FirestoreClient.getFirestore()
    )
    val transactionBusiness = TransactionBusiness(transactionRepository)

    install(ContentNegotiation) {
        json()
    }
    configureRouting(transactionBusiness)
}

fun Application.configureRouting(transactionBusiness: TransactionBusiness) {
    routing {
        post("/payments") {
            val request = call.receive<InitiatePaymentRequest>()

            if (request.amount <= 0.0) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ApiResponse(
                        message = "Invalid amount. Payment amount must be positive.",
                        success = false,
                        data = null
                    )
                )
                return@post
            }

            val transaction = transactionBusiness.initiatePayment(request)
            call.respond(
                HttpStatusCode.Created,
                ApiResponse(
                    message = "Payment request received and transaction initiated.",
                    success = true,
                    data = transaction
                )
            )
        }
    }
}

fun initializeFirebase() {
    val serviceAccountStream = Application::class.java.classLoader
        .getResourceAsStream("serviceAccountKey.json")
        ?: throw Exception("Firebase service account key not found. Make sure 'serviceAccountKey.json' is in the resources folder.")

    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
        .build()

    if (com.google.firebase.FirebaseApp.getApps().isEmpty()) {
        com.google.firebase.FirebaseApp.initializeApp(options)
    }
}
