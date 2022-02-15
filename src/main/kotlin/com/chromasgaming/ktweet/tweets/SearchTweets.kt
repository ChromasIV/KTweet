package com.chromasgaming.ktweet.tweets

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.dtos.TweetObject
import io.ktor.client.call.receive
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.UrlEncodingOption
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject


class SearchTweets {

    private val json = Json {
        encodeDefaults = false
        ignoreUnknownKeys = true
    }

    suspend fun search(paramMap: LinkedHashMap<String, String>, authorizationHeaderString: String): List<TweetObject> {
        var listTweetObject: List<TweetObject> = listOf()
        runBlocking {
            val client = ClientConfig()
            val builder = HttpRequestBuilder()
            builder.url("$BASEURL/$VERSION/tweets/search/recent")

            builder.url.parameters.urlEncodingOption = UrlEncodingOption.NO_ENCODING
            paramMap.forEach { (key, value) ->
                builder.url.parameters.append(key, value)
            }

            builder.headers.append(HttpHeaders.Authorization, authorizationHeaderString)
            builder.headers.append(HttpHeaders.ContentType, "application/json")

            val response = client.get(builder)
            var jsonString: JsonElement? = null
            val resultCount = json.decodeFromString<JsonObject>(response.receive())["meta"]

            if (resultCount != null) {
                if (resultCount.jsonObject["result_count"].toString() > "0") {
                    jsonString = json.decodeFromString<JsonObject>(response.receive())["data"]
                    listTweetObject = json.decodeFromString(jsonString.toString())
                }
            }
            client.close()
        }

        return listTweetObject
    }
}
