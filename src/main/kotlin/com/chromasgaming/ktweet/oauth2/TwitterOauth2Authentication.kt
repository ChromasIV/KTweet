package com.chromasgaming.ktweet.oauth2

import com.chromasgaming.ktweet.config.MyHttpClient
import com.chromasgaming.ktweet.models.BearerToken
import com.chromasgaming.ktweet.util.BASEURL
import com.chromasgaming.ktweet.util.HttpRequestBuilderWrapper
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.append
import java.util.*

class TwitterOauth2Authentication(
    client: MyHttpClient = MyHttpClient()
) {

    private val httpClient = client.httpClient
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

        val basicAuth = Base64.getEncoder().encodeToString("$clientId:$clientSecret".toByteArray())

        val formParameters = Parameters.build {
            append("code", code)
            append("grant_type", "authorization_code")
            append("client_id", clientId)
            append("redirect_uri", redirectUri)
            append("code_verifier", PKCE)
        }

        val response: HttpResponse = httpClient.submitForm(
            url = "$BASEURL/2/oauth2/token",
            formParameters = formParameters
        ) {
            headers {
                append(HttpHeaders.Authorization, "Basic $basicAuth")
                append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
            }
        }

        val bearerToken: BearerToken = response.body()
        httpClient.close()

        return bearerToken

    }

    //OAuth 2.0 App-Only
    suspend fun getAppOnlyBearerToken(apiKey: String, apiSecretKey: String): BearerToken {
        val basic = Base64.getEncoder().encodeToString("$apiKey:$apiSecretKey".toByteArray())

        val builder = HttpRequestBuilderWrapper("$BASEURL/oauth2/token") {
            parameter("grant_type", "client_credentials")
            headers {
                append(
                    HttpHeaders.Authorization,
                    "Basic $basic"
                )
                append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
            }
            method = HttpMethod.Post
        }.build()

        val response: HttpResponse = httpClient.request(builder)
        val bearerToken: BearerToken = response.body()
        httpClient.close()

        return bearerToken

    }
}
