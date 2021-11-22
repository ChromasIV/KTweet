package com.chromasgaming.ktweet.config

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

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