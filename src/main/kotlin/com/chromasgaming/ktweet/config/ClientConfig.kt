package com.chromasgaming.ktweet.config

import com.chromasgaming.ktweet.util.defaultJson
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.append

class ClientConfig {

    private val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.NONE
        }
        install(ContentNegotiation) {
            defaultJson
        }
    }

    suspend fun execute(builder: HttpRequestBuilder): HttpResponse = client.request(builder)

    suspend fun submitForm(url: String, formParameters: Parameters, basicAuth: String) =
        client.submitForm(url, formParameters) {
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Basic $basicAuth"
                )
                append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
            }
        }

    fun close() = client.close()
}
