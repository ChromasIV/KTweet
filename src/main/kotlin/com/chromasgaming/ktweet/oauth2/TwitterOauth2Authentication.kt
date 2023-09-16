package com.chromasgaming.ktweet.oauth2

import com.chromasgaming.ktweet.config.MyHttpClient
import com.chromasgaming.ktweet.models.BearerToken
import com.chromasgaming.ktweet.util.BASEURL
import com.chromasgaming.ktweet.util.HttpRequestBuilderWrapper
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
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

        val response: HttpResponse = httpClient.post("$BASEURL/2/oauth2/token") {
            headers {
                append(HttpHeaders.Authorization, "Basic $basicAuth")
                append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
            }
            setBody(formParameters.formUrlEncode())

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
