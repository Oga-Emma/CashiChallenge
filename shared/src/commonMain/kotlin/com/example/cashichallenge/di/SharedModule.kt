package com.example.cashichallenge.di

import com.example.cashichallenge.data.remote.PaymentApi
import com.example.cashichallenge.data.remote.RemotePaymentApiImpl
import com.example.cashichallenge.data.repository.FirebaseTransactionDataSource
import com.example.cashichallenge.data.repository.TransactionDataSource
import com.example.cashichallenge.domain.getPlatform
import com.example.cashichallenge.domain.usecase.GetTransactionsUseCase
import com.example.cashichallenge.domain.usecase.SendPaymentUseCase
import com.example.cashichallenge.factory.getFirestore
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val baseUrl = "https://tomato-qty-drawings-hired.trycloudflare.com"

val sharedModule = module {
    single { getPlatform() }
    single { getFirestore() }

    single {
        HttpClient() {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = baseUrl.split("://")[1]
                }
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d(message)
                    }
                }
            }

            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true })
            }

            install(WebSockets) {
                pingIntervalMillis = 20_000
            }
        }
    }

    singleOf(::FirebaseTransactionDataSource) { bind<TransactionDataSource>() }
    singleOf(::RemotePaymentApiImpl) { bind<PaymentApi>() }
    singleOf(::GetTransactionsUseCase)
    singleOf(::SendPaymentUseCase)
}
