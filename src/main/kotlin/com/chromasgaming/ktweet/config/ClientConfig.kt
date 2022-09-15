package com.chromasgaming.ktweet.config

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*

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

    suspend fun get(builder: HttpRequestBuilder) = client.get(builder)
    suspend fun post(builder: HttpRequestBuilder) = client.post(builder)
    suspend fun delete(builder: HttpRequestBuilder) = client.delete(builder)

    fun close() = client.close()
}
