package com.chromasgaming.ktweet.tweets

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.dtos.BearerToken
import com.chromasgaming.ktweet.dtos.TweetCount
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

enum class Granularity {
    day,
    hour,
    minute
}

class CountTweets {

    //https://api.twitter.com/2/tweets/counts/recent?query=from%3ATwitterDev&granularity=day
    private val json = Json {
        encodeDefaults = false
        ignoreUnknownKeys = true
    }

    suspend fun recent(paramMap: LinkedHashMap<String, String>, bearerToken: BearerToken): TweetCount {
        var response: HttpResponse
        runBlocking {
            val client = ClientConfig()
            val builder = HttpRequestBuilder()
            builder.url("$BASEURL/$VERSION/tweets/counts/recent")

            paramMap.forEach { (key, value) ->
                builder.url.parameters.append(key, value)
            }

            builder.headers.append(HttpHeaders.Authorization, "Bearer ${bearerToken.access_token}")
            builder.headers.append(HttpHeaders.ContentType, "application/json")
            response = client.get(builder)

            client.close()
        }

        return response.body()
    }
}