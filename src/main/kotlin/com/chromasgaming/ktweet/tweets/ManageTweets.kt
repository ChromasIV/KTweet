package com.chromasgaming.ktweet.tweets

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.dtos.ManageTweetsDTO
import com.chromasgaming.ktweet.dtos.TweetDTO
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

/**
 * This class handles all API calls that exist under ManagerTweets.
 * API Reference [Twitter API](https://developer.twitter.com/en/docs/twitter-api/tweets/manage-tweets/api-reference).
 */
@ExperimentalSerializationApi
class ManageTweets {
    private val json = Json { encodeDefaults = false }


    /**
     * Creates a [tweetDTO].
     * @return the response in the object [ManageTweetsDTO]
     */
    suspend fun create(tweetDTO: TweetDTO, authorizationHeaderString: String): ManageTweetsDTO {
        val stringBody: ManageTweetsDTO
        runBlocking {
            val client = ClientConfig()
            val builder = HttpRequestBuilder()
            builder.url("$BASEURL/$VERSION/tweets")

            builder.setBody(tweetDTO)
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
     * @return the response in the object [ManageTweetsDTO]
     */
    suspend fun destroy(id: String, authorizationHeaderString: String): ManageTweetsDTO {

        val stringBody: ManageTweetsDTO
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
