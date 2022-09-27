package com.chromasgaming.ktweet.api

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.models.ManageTweets
import com.chromasgaming.ktweet.models.Tweet
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi

/**
 * This class handles all API calls that exist under ManagerTweets.
 * API Reference [Twitter API](https://developer.twitter.com/en/docs/twitter-api/tweets/manage-tweets/api-reference).
 */
@ExperimentalSerializationApi
class ManageTweetsAPI {

    /**
     * Creates a [tweet].
     * @return the response in the object [ManageTweets]
     */
    suspend fun create(tweet: Tweet, authorizationHeaderString: String): ManageTweets {
        val stringBody: ManageTweets
        runBlocking {
            val client = ClientConfig()
            val builder = HttpRequestBuilder()
            builder.url("$BASEURL/$VERSION/tweets")
            builder.setBody(tweet)
            builder.headers.append(HttpHeaders.Authorization, authorizationHeaderString)
            builder.headers.append(HttpHeaders.ContentType, "application/json")

            val response = client.post(builder)
            stringBody = response.body()
            client.close()
        }
        return stringBody
    }

    /**
     * Deletes a tweet by [id].
     * @return the response in the object [ManageTweets]
     */
    suspend fun destroy(id: String, authorizationHeaderString: String): ManageTweets {

        val stringBody: ManageTweets
        runBlocking {
            val client = ClientConfig()
            val builder = HttpRequestBuilder()
            builder.url("$BASEURL/$VERSION/tweets/$id")
            builder.headers.append(HttpHeaders.Authorization, authorizationHeaderString)

            val response = client.delete(builder)
            stringBody = response.body()
            client.close()
        }
        return stringBody
    }
}
