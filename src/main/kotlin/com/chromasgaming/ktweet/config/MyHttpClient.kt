package com.chromasgaming.ktweet.config

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class MyHttpClient(val httpClient: HttpClient) {

    constructor() : this(createHttpClient())

    private companion object {
        fun createHttpClient(): HttpClient = HttpClient(CIO) {
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
    }
}


