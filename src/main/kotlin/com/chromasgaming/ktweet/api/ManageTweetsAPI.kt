package com.chromasgaming.ktweet.api

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.models.ManageTweets
import com.chromasgaming.ktweet.models.Tweet
import com.chromasgaming.ktweet.util.HttpRequestBuilderWrapper
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.append
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import kotlinx.serialization.ExperimentalSerializationApi
import mu.KotlinLogging


/**
 * This interface defines the API endpoints for managing tweets.
 */
interface ManageTweetsApi {
    /**
     * Creates a new [Tweet].
     * @param tweet the [Tweet] object to create
     * @return the response in the object [ManageTweets]
     */
    suspend fun create(tweet: Tweet): ManageTweets

    /**
     * Deletes a tweet by [id].
     * @param id the id of the tweet to delete
     * @return the response in the object [ManageTweets]
     */
    suspend fun destroy(id: String): ManageTweets
}

/**
 * This class implements the [ManageTweetsApi] interface using the Twitter API.
 * @param clientConfig the [ClientConfig] object to use for HTTP requests
 * @param authorizationHeaderString the authorization header string to use for requests
 */
@ExperimentalSerializationApi
class TwitterManageTweetsApi(
    private val clientConfig: ClientConfig,
    private val authorizationHeaderString: String
) : ManageTweetsApi {

    private val logger = KotlinLogging.logger {}

    /**
     * Creates a [Tweet].
     * @param tweet the [Tweet] object to create
     * @return the response in the object [ManageTweets]
     */
    override suspend fun create(tweet: Tweet): ManageTweets {
        val builder = HttpRequestBuilderWrapper("$BASEURL/$VERSION/tweets") {
            method = HttpMethod.Post
            setBody(tweet)
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
                append(HttpHeaders.Authorization, authorizationHeaderString)
            }
        }.build()

        return execute(builder)
    }

    /**
     * Deletes a tweet by [id].
     * @param id the id of the tweet to delete
     * @return the response in the object [ManageTweets]
     */
    override suspend fun destroy(id: String): ManageTweets {
        val builder = HttpRequestBuilderWrapper("$BASEURL/$VERSION/tweets/$id") {
            method = HttpMethod.Delete
            headers {
                append(HttpHeaders.Authorization, authorizationHeaderString)
            }
        }.build()

        return execute(builder)
    }

    /**
     * Executes an HTTP request and returns the response in a [ManageTweets] object.
     * @param builder the [HttpRequestBuilder] object representing the HTTP request
     * @return the response in the object [ManageTweets]
     * @throws RedirectResponseException, ClientRequestException, or ServerResponseException if the HTTP request fails
     */
    private suspend fun execute(builder: HttpRequestBuilder): ManageTweets {
        try {
            val response = clientConfig.execute(builder)
            if (response.status.isSuccess()) {
                val body = response.call.body<ManageTweets>()
                logger.debug("API request successful: ${builder.url.encodedPath}")
                return body
            } else {
                logger.warn("API request failed: ${builder.url.encodedPath}, ${response.status}")
                val exception = when (response.status.value) {
                    in 300..399 -> RedirectResponseException(response, "")
                    in 400..499 -> ClientRequestException(response, "")
                    else -> ServerResponseException(response, "")
                }

                throw exception
            }
        } catch (e: Exception) {
            logger.error("API request failed: ${builder.url.encodedPath}, ${e.message}")
            throw e
        }
    }
}
