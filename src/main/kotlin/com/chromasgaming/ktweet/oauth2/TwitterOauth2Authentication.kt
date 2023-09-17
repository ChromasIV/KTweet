package com.chromasgaming.ktweet.oauth2

import com.chromasgaming.ktweet.config.MyHttpClient
import com.chromasgaming.ktweet.models.OAuth2
import com.chromasgaming.ktweet.util.BASEURL
import com.chromasgaming.ktweet.util.HttpRequestBuilderWrapper
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.append
import io.ktor.http.formUrlEncode
import java.util.*

class TwitterOauth2Authentication(
    client: MyHttpClient = MyHttpClient()
) {

    private val httpClient = client.httpClient
    /**
     * OAuth 2.0 making request on behalf of users (Confidential)
     * @return the response in the object [OAuth2]
     */
    suspend fun getBearerToken(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        pkce: String
    ): OAuth2 {

        val basicAuth = Base64.getEncoder().encodeToString("$clientId:$clientSecret".toByteArray())

        val formParameters = Parameters.build {
            append("code", code)
            append("grant_type", "authorization_code")
            append("client_id", clientId)
            append("redirect_uri", redirectUri)
            append("code_verifier", pkce)
        }

        val response: HttpResponse = httpClient.post("$BASEURL/2/oauth2/token") {
            headers {
                append(HttpHeaders.Authorization, "Basic $basicAuth")
                append(HttpHeaders.ContentType, ContentType.Application.FormUrlEncoded)
            }
            setBody(formParameters.formUrlEncode())

        }

        val oAuth2: OAuth2 = response.body()
        httpClient.close()

        return oAuth2

    }

    //OAuth 2.0 App-Only
    suspend fun getAppOnlyBearerToken(apiKey: String, apiSecretKey: String): OAuth2 {
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
        val oAuth2: OAuth2 = response.body()
        httpClient.close()

        return oAuth2

    }
}
