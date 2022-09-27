package com.chromasgaming.ktweet.oauth

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.constants.NINE
import com.chromasgaming.ktweet.constants.THIRTEEN
import com.chromasgaming.ktweet.constants.TWELVE
import com.chromasgaming.ktweet.constants.TWENTY
import com.chromasgaming.ktweet.constants.TWENTYSIX
import com.chromasgaming.ktweet.models.AccessToken
import com.chromasgaming.ktweet.models.RequestToken
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders

class TwitterAuthentication {

    suspend fun getRequestToken(consumerKey: String, consumerSecret: String): RequestToken {
        val client = ClientConfig()
        val builder = HttpRequestBuilder()

        val signatureBuilder = SignatureBuilder.Builder()
            .oauthConsumerKey(consumerKey)
            .oauthConsumerSecret(consumerSecret)
            .build()

        val authorizationHeaderString =
            buildSignature(
                "POST",
                signatureBuilder,
                "oauth/request_token",
                emptyMap()
            )

        builder.url("$BASEURL/oauth/request_token")
        builder.headers.append(HttpHeaders.Authorization, authorizationHeaderString)

        val response: HttpResponse = client.post(builder)

        val stringBody: String = response.body()
        client.close()
        return RequestToken(
            stringBody.substring(
                stringBody.indexOf("oauth_token=") + TWELVE,
                stringBody.indexOf("&oauth_token_secret")
            ),
            stringBody.substring(
                stringBody.indexOf("&oauth_token_secret=") + TWENTY,
                stringBody.indexOf("&oauth_callback_confirmed")
            ),
            stringBody.substring(stringBody.indexOf("&oauth_callback_confirmed=") + TWENTYSIX)
        )
    }

    suspend fun getAccessToken(
        consumerKey: String,
        consumerSecret: String,
        authToken: String,
        authVerifier: String
    ): AccessToken {
        val client = ClientConfig()
        val builder = HttpRequestBuilder()

        val signatureBuilder = SignatureBuilder.Builder()
            .oauthConsumerKey(consumerKey)
            .oauthConsumerSecret(consumerSecret)
            .build()

        val authorizationHeaderString = buildSignature(
            "POST",
            signatureBuilder,
            "/oauth/access_token?oauth_token=",
            emptyMap()
        )

        builder.url("$BASEURL/oauth/access_token?oauth_token=$authToken&oauth_verifier=$authVerifier")
        builder.headers.append(HttpHeaders.Authorization, authorizationHeaderString)

        val response: HttpResponse = client.post(builder)

        val stringBody: String = response.body()
        val authTokenString = stringBody.substring(
            stringBody.indexOf("oauth_token=") + TWELVE,
            stringBody.indexOf("&oauth_token_secret=")
        )
        val authSecret = stringBody.substring(
            stringBody.indexOf("&oauth_token_secret=") + TWENTY,
            stringBody.indexOf("&user_id")
        )
        val userId = stringBody.substring(stringBody.indexOf("&user_id=") + NINE, stringBody.indexOf("&screen_name"))
        val userName = stringBody.substring(stringBody.indexOf("&screen_name=") + THIRTEEN)
        client.close()

        return AccessToken(authTokenString, authSecret, userId, userName)
    }
}
