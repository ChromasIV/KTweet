package com.chromasgaming.ktweet.tweets

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.dto.PostTweetDTO
import com.chromasgaming.ktweet.dto.Tweet
import com.chromasgaming.ktweet.oauth.SignatureBuilder
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

class ManageTweets {
    private val json = Json { encodeDefaults = false }

    suspend fun create(tweet: Tweet): PostTweetDTO {

        val authorizationHeaderString = SignatureBuilder().buildSignature(
            "POST",
            System.getenv("consumerKey"),
            System.getenv("consumerSecret"),
            System.getenv("accessToken"),
            System.getenv("accessTokenSecret"),
            "",
            "$VERSION/tweets"
        )
        val stringBody: PostTweetDTO
        runBlocking {
            val client = ClientConfig()
            val builder = HttpRequestBuilder()
            builder.url("$BASEURL/$VERSION/tweets")

            builder.body = json.decodeFromString<JsonObject>(json.encodeToString(tweet))
            builder.headers.append(HttpHeaders.Authorization, authorizationHeaderString)
            builder.headers.append(HttpHeaders.ContentType, "application/json")

            val response = client.post(builder)
            stringBody = response.receive()
            println(stringBody)
            client.close()
        }
        return stringBody
    }

    suspend fun destroy(id: String) {
        val authorizationHeaderString = SignatureBuilder().buildSignature(
            "DELETE",
            System.getenv("consumerKey"),
            System.getenv("consumerSecret"),
            System.getenv("accessToken"),
            System.getenv("accessTokenSecret"),
            "",
            "$VERSION/tweets/$id"
        )
        runBlocking {
            val client = ClientConfig()
            val builder = HttpRequestBuilder()
            builder.url("$BASEURL/$VERSION/tweets/$id")
            builder.headers.append(HttpHeaders.Authorization, authorizationHeaderString)

            val response = client.delete(builder)
            val stringBody: String = response.receive()
            println(stringBody)

            client.close()
        }
    }
}