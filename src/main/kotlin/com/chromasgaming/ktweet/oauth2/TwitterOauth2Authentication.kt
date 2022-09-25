package com.chromasgaming.ktweet.oauth2

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.BASEURL
import com.chromasgaming.ktweet.dtos.BearerToken
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.util.*

class TwitterOauth2Authentication {

    //OAuth 2.0 making request on behalf of users
    suspend fun getBearerToken(code: String, clientId: String, clientSecret: String): BearerToken {
        val client = ClientConfig()
        val basicAuth = Base64.getEncoder().encodeToString("$clientId:$clientSecret".toByteArray())

        val formParameters = Parameters.build {
            append("code", code)
            append("grant_type", "authorization_code")
            append("client_id", System.getProperty("clientId"))
            //TODO: FIX Redirect
            append("redirect_uri", "http://localhost/oauth2")
            append("code_verifier", "challenge")
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
        }

        val response: HttpResponse = client.post(builder)

        val bearerToken: BearerToken = response.body()
        client.close()

        return bearerToken
    }
}