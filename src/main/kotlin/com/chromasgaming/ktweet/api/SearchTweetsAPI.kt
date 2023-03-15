package com.chromasgaming.ktweet.api

import TwitterSearch
import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.models.TweetObject
import com.chromasgaming.ktweet.util.HttpRequestBuilderWrapper
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.append
import io.ktor.http.isSuccess

/**
 * This class handles all API calls that exist under Search Tweets.
 * API Reference [Twitter API](https://developer.twitter.com/en/docs/twitter-api/tweets/search/api-reference).
 */
class SearchTweetsAPI(private val client: ClientConfig) {
    /**
     *  Search for Tweets published in the last 7 days
     *  @return the response in the object List[TweetObject]
     */
    suspend fun searchTweet(
        paramMap: LinkedHashMap<String, String>,
        authorizationHeaderString: String
    ): List<TweetObject> {
        var listTweetObject: List<TweetObject> = listOf()

        val builder = HttpRequestBuilderWrapper("$BASEURL/$VERSION/tweets/search/recent") {
            method = HttpMethod.Get
            paramMap.forEach { (key, value) ->
                url.encodedParameters.append(key, value)
            }
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
                append(HttpHeaders.Authorization, authorizationHeaderString)
            }
        }.build()

        val response = client.execute(builder)
        if (response.status.isSuccess()) {
            val body = response.body<TwitterSearch>()

            if (body.meta.resultCount > 0) {
                listTweetObject = body.tweets
            }
        }
        client.close()

        return listTweetObject
    }
}
