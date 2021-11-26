package com.chromasgaming.ktweet.oauth

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.dtos.AccessTokenDTO
import com.chromasgaming.ktweet.dtos.RequestTokenDTO
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

class TwitterAuthentication {
    private var signatureBuilder = SignatureBuilder()

    suspend fun getRequestToken(consumerKey: String, consumerSecret: String): RequestTokenDTO {
        val client = ClientConfig()
        val builder = HttpRequestBuilder()

        val authorizationHeaderString =
            signatureBuilder.buildSignature(
                "POST",
                consumerKey, consumerSecret, null, null,
                "oauth/request_token"
            )

        builder.url("$BASEURL/oauth/request_token")
        builder.headers.append(HttpHeaders.Authorization, authorizationHeaderString)

        val response: HttpResponse = client.post(builder)

        val stringBody: String = response.receive()
        client.close()
        return RequestTokenDTO(
            stringBody.substring(
                stringBody.indexOf("oauth_token=") + 12,
                stringBody.indexOf("&oauth_token_secret")
            ),
            stringBody.substring(
                stringBody.indexOf("&oauth_token_secret=") + 20,
                stringBody.indexOf("&oauth_callback_confirmed")
            ),
            stringBody.substring(stringBody.indexOf("&oauth_callback_confirmed=") + 26)
        )
    }

    suspend fun getAccessToken(
        consumerKey: String,
        consumerSecret: String,
        authToken: String,
        authVerifier: String
    ): AccessTokenDTO {
        val client = ClientConfig()
        val builder = HttpRequestBuilder()

        val authorizationHeaderString = signatureBuilder.buildSignature(
            "POST",
            consumerKey,
            consumerSecret,
            null,
            null,
            "/oauth/access_token?oauth_token="
        )

        builder.url("$BASEURL/oauth/access_token?oauth_token=$authToken&oauth_verifier=$authVerifier")
        builder.headers.append(HttpHeaders.Authorization, authorizationHeaderString)

        val response: HttpResponse = client.post(builder)

        val stringBody: String = response.receive()
        val authTokenString = stringBody.substring(
            stringBody.indexOf("oauth_token=") + 12,
            stringBody.indexOf("&oauth_token_secret=")
        )
        val authSecret = stringBody.substring(
            stringBody.indexOf("&oauth_token_secret=") + 20,
            stringBody.indexOf("&user_id")
        )
        val userId = stringBody.substring(stringBody.indexOf("&user_id=") + 9, stringBody.indexOf("&screen_name"))
        val userName = stringBody.substring(stringBody.indexOf("&screen_name=") + 13)
        client.close()

        return AccessTokenDTO(authTokenString, authSecret, userId, userName)
    }
}

