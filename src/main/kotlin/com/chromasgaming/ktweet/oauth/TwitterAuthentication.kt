package com.chromasgaming.ktweet.oauth

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.models.AccessToken
import com.chromasgaming.ktweet.models.RequestToken
import com.chromasgaming.ktweet.util.BASEURL
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod

class TwitterAuthentication {

    private val client = ClientConfig()

    suspend fun getRequestToken(consumerKey: String, consumerSecret: String): RequestToken {
        val builder = HttpRequestBuilder()
        builder.url("$BASEURL/oauth/request_token")
        builder.headers.append(
            HttpHeaders.Authorization,
            createAuthorizationHeaderString(consumerKey, consumerSecret, "POST", "/oauth/request_token")
        )
        builder.method = HttpMethod.Post

        val response: HttpResponse = client.execute(builder)
        val stringBody: String = response.body()

        client.close()

        return RequestToken(
            getTokenValue(stringBody, "oauth_token="),
            getTokenValue(stringBody, "&oauth_token_secret="),
            getTokenValue(stringBody, "&oauth_callback_confirmed=")
        )
    }

    suspend fun getAccessToken(
        consumerKey: String,
        consumerSecret: String,
        authToken: String,
        authVerifier: String
    ): AccessToken {
        val builder = HttpRequestBuilder()
        builder.url("$BASEURL/oauth/access_token?oauth_token=$authToken&oauth_verifier=$authVerifier")
        builder.headers.append(
            HttpHeaders.Authorization,
            createAuthorizationHeaderString(consumerKey, consumerSecret, "POST", "/oauth/access_token")
        )
        builder.method = HttpMethod.Post

        val response: HttpResponse = client.execute(builder)
        val stringBody: String = response.body()

        client.close()

        return AccessToken(
            getTokenValue(stringBody, "oauth_token="),
            getTokenValue(stringBody, "&oauth_token_secret="),
            getTokenValue(stringBody, "&user_id="),
            getTokenValue(stringBody, "&screen_name=")
        )
    }

    private fun createAuthorizationHeaderString(
        consumerKey: String,
        consumerSecret: String,
        method: String,
        endpoint: String
    ): String {
        val signatureBuilder = SignatureBuilder.Builder()
            .oauthConsumerKey(consumerKey)
            .oauthConsumerSecret(consumerSecret)
            .build()

        return buildSignature(method, signatureBuilder, endpoint, emptyMap())
    }

    private fun getTokenValue(stringBody: String, key: String): String {
        return stringBody.substring(
            stringBody.indexOf(key) + key.length,
            stringBody.indexOf("&", startIndex = stringBody.indexOf(key) + key.length)
        )
    }
}
