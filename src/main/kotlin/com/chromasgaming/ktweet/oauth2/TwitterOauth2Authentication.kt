package com.chromasgaming.ktweet.oauth2

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.models.BearerToken
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import java.util.*

class TwitterOauth2Authentication {

    /**
     * OAuth 2.0 making request on behalf of users (Confidential)
     * @return the response in the object [BearerToken]
     */
    suspend fun getBearerToken(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        PKCE: String
    ): BearerToken {
        val client = ClientConfig()
        val basicAuth = Base64.getEncoder().encodeToString("$clientId:$clientSecret".toByteArray())

        val formParameters = Parameters.build {
            append("code", code)
            append("grant_type", "authorization_code")
            append("client_id", clientId)
            append("redirect_uri", redirectUri)
            append("code_verifier", PKCE)
        }
        val response: HttpResponse = client.submitForm("$BASEURL/2/oauth2/token", formParameters, basicAuth)

        val bearerToken: BearerToken = response.body()
        client.close()

        return bearerToken
    }

    //OAuth 2.0 App-Only
    suspend fun getAppOnlyBearerToken(apiKey: String, apiSecretKey: String): BearerToken {
        val client = ClientConfig()
        val builder = HttpRequestBuilder()

        val basic = Base64.getEncoder().encodeToString("$apiKey:$apiSecretKey".toByteArray())

        builder.url("$BASEURL/oauth2/token")
        builder.parameter("grant_type", "client_credentials")

        builder.headers {
            append(
                HttpHeaders.Authorization,
                "Basic $basic"
            )
            append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
        }

        val response: HttpResponse = client.post(builder)

        val bearerToken: BearerToken = response.body()
        client.close()

        return bearerToken
    }
}
