package com.chromasgaming.ktweet.api

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.models.BearerToken
import com.chromasgaming.ktweet.models.TweetCount
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import kotlinx.coroutines.runBlocking

/**
 * This class handles all API calls that exist under Tweet Counts.
 * API Reference [Twitter API](https://developer.twitter.com/en/docs/twitter-api/tweets/counts/api-reference).
 */
class CountTweetsAPI {

    /**
     * Receive a count of Tweets [TweetCount] that match a query in the last 7 days
     * @return the response in the object [TweetCount]
     */
    suspend fun recent(paramMap: LinkedHashMap<String, String>, bearerToken: BearerToken): TweetCount {
        var response: HttpResponse
        runBlocking {
            val client = ClientConfig()
            val builder = HttpRequestBuilder()
            builder.method = HttpMethod.Get
            builder.url("$BASEURL/$VERSION/tweets/counts/recent")

            paramMap.forEach { (key, value) ->
                builder.url.parameters.append(key, value)
            }

            builder.headers.append(HttpHeaders.Authorization, "Bearer ${bearerToken.access_token}")
            builder.headers.append(HttpHeaders.ContentType, "application/json")
            response = client.execute(builder)

            client.close()
        }

        return response.body()
    }
}
