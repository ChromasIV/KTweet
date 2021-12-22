package com.chromasgaming.ktweet.tweets

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.dtos.TweetObject
import com.chromasgaming.ktweet.oauth.SignatureBuilder
import io.ktor.client.call.receive
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.UrlEncodingOption
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import java.net.URLEncoder


class SearchTweets {

    private val json = Json { encodeDefaults = false }

    suspend fun search(paramMap: LinkedHashMap<String, String>): List<TweetObject> {
        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val authorizationHeaderString = SignatureBuilder().buildSignature(
            "GET",
            System.getenv("consumerKey"),
            System.getenv("consumerSecret"),
            System.getenv("accessToken"),
            System.getenv("accessTokenSecret"),
            "$VERSION/tweets/search/recent",
            paramMap
        )
        val stringBody: List<TweetObject>
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
            stringBody = json.decodeFromString<JsonObject>(response.receive())["data"] as List<TweetObject>
            client.close()
        }

        return stringBody
    }
}
