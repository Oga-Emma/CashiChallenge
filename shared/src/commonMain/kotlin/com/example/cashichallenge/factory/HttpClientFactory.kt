package com.example.cashichallenge.factory

import com.example.cashichallenge.domain.util.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            defaultRequest {
                contentType(ContentType.Application.Json)
                url {
                    protocol = URLProtocol.HTTPS
                    host = Constants.BASE_URL.split("://")[1]
                }
            }
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true })
            }
            install(Logging)
            install(WebSockets) {
                pingIntervalMillis = 20_000
            }
        }
    }
}