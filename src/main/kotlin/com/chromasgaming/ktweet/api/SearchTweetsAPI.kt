package com.chromasgaming.ktweet.api

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.models.TweetObject
import com.chromasgaming.ktweet.util.defaultJson
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

/**
 * This class handles all API calls that exist under Search Tweets.
 * API Reference [Twitter API](https://developer.twitter.com/en/docs/twitter-api/tweets/search/api-reference).
 */
class SearchTweetsAPI {
    /**
     *  Search for Tweets published in the last 7 days
     *  @return the response in the object List[TweetObject]
     */
    suspend fun search(paramMap: LinkedHashMap<String, String>, authorizationHeaderString: String): List<TweetObject> {
        var listTweetObject: List<TweetObject> = listOf()
        runBlocking {
            val client = ClientConfig()
            val builder = HttpRequestBuilder()
            builder.url("$BASEURL/$VERSION/tweets/search/recent")

            paramMap.forEach { (key, value) ->
                builder.url.parameters.append(key, value)
            }

            builder.headers.append(HttpHeaders.Authorization, authorizationHeaderString)
            builder.headers.append(HttpHeaders.ContentType, "application/json")

            val response = client.get(builder)
            val jsonString: JsonElement?
            val resultCount = defaultJson.decodeFromString<JsonObject>(response.body())["meta"]

            if (resultCount != null) {
                if (resultCount.jsonObject["result_count"].toString() > "0") {
                    jsonString = defaultJson.decodeFromString<JsonObject>(response.body())["data"]
                    listTweetObject = defaultJson.decodeFromString(jsonString.toString())
                }
            }
            client.close()
        }

        return listTweetObject
    }
}
