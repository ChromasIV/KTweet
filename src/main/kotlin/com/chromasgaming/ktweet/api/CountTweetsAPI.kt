package com.chromasgaming.ktweet.api

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.models.BearerToken
import com.chromasgaming.ktweet.models.TweetCount
import com.chromasgaming.ktweet.util.BASEURL
import com.chromasgaming.ktweet.util.HttpRequestBuilderWrapper
import com.chromasgaming.ktweet.util.VERSION
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.append

private val client = ClientConfig()

/**
 * This class handles all API calls that exist under Tweet Counts.
 * API Reference [Twitter API](https://developer.twitter.com/en/docs/twitter-api/tweets/counts/api-reference).
 */
class CountTweetsAPI() {

    /**
     * Receive a count of Tweets [TweetCount] that match a query in the last 7 days
     * @return the response in the object [TweetCount]
     */
    suspend fun recent(paramMap: LinkedHashMap<String, String>, bearerToken: BearerToken): TweetCount {
        var response: HttpResponse

        val builder = HttpRequestBuilderWrapper("$BASEURL/$VERSION/tweets/counts/recent") {
            method = HttpMethod.Get
            paramMap.forEach { (key, value) ->
                url.encodedParameters.append(key, value)
            }
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
                append(HttpHeaders.Authorization, "Bearer ${bearerToken.access_token}")
            }
        }.build()

        response = client.execute(builder)

        client.close()


        return response.body()
    }
}
