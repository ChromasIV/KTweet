package com.chromasgaming.ktweet.util

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url

class HttpRequestBuilderWrapper(url: String, block: HttpRequestBuilder.() -> Unit) {
    private val builder: HttpRequestBuilder = HttpRequestBuilder()

    init {
        builder.url(url)
        block(builder)
    }

    fun build(): HttpRequestBuilder {
        return builder
    }
}
