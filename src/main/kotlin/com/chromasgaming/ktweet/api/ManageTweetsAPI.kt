package com.chromasgaming.ktweet.api

import com.chromasgaming.ktweet.config.MyHttpClient
import com.chromasgaming.ktweet.models.ManageTweets
import com.chromasgaming.ktweet.models.Tweet
import com.chromasgaming.ktweet.util.BASEURL
import com.chromasgaming.ktweet.util.CLIENT_ERROR_RANGE
import com.chromasgaming.ktweet.util.HttpRequestBuilderWrapper
import com.chromasgaming.ktweet.util.REDIRECT_RANGE
import com.chromasgaming.ktweet.util.VERSION
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.append
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import kotlinx.serialization.ExperimentalSerializationApi


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
 * @param client the [MyHttpClient] object to use for HTTP requests
 * @param authorizationHeaderString the authorization header string to use for requests
 */
@ExperimentalSerializationApi
class TwitterManageTweetsApi(
    private val authorizationHeaderString: String,
    client: MyHttpClient = MyHttpClient()
) : ManageTweetsApi {

    private val httpClient = client.httpClient
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
    @Suppress("TooGenericExceptionCaught")
    private suspend fun execute(builder: HttpRequestBuilder): ManageTweets {
        try {
            val response = httpClient.request(builder)
            if (response.status.isSuccess()) {
                val body = response.call.body<ManageTweets>()
                logger.debug { "API request successful: ${builder.url.encodedPath}" }
                return body
            } else {
                logger.warn { "API request failed: ${builder.url.encodedPath}, ${response.status}" }
                val exception = when (response.status.value) {
                    in REDIRECT_RANGE -> RedirectResponseException(response, "")
                    in CLIENT_ERROR_RANGE -> ClientRequestException(response, "")
                    else -> ServerResponseException(response, "")
                }

                throw exception
            }
        } catch (e: Exception) {
            logger.error { "API request failed: ${builder.url.encodedPath}, ${e.message}" }
            throw e
        }
    }
}
