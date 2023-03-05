package com.chromasgaming.ktweet.config

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ClientConfig {

    private val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.NONE
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun execute(builder: HttpRequestBuilder): HttpResponse = client.request(builder)

    suspend fun get(builder: HttpRequestBuilder) = client.get(builder)
    suspend fun post(builder: HttpRequestBuilder) = client.post(builder)
    suspend fun delete(builder: HttpRequestBuilder) = client.delete(builder)

    suspend fun submitForm(url: String, formParameters: Parameters, basicAuth: String) =
        client.submitForm(url, formParameters) {
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Basic $basicAuth"
                )
                append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
            }
        }

    fun close() = client.close()
}
