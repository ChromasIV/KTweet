package com.chromasgaming.ktweet.config

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.request.post
import io.ktor.client.request.delete
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse

class ClientConfig {

    private val client = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(json = kotlinx.serialization.json.Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
        }
    }

    suspend fun post(builder: HttpRequestBuilder) = client.post<HttpResponse>(builder)
    suspend fun delete(builder: HttpRequestBuilder) = client.delete<HttpResponse>(builder)

    fun close() = client.close()
}
