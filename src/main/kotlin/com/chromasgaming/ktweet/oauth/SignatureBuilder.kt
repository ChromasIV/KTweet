package com.chromasgaming.ktweet.oauth

import com.chromasgaming.ktweet.constants.BASEURL
import io.ktor.http.auth.*
import java.net.URLEncoder
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

internal class SignatureBuilder {

    fun buildSignature(
        requestMethod: String,
        oauthConsumerKey: String,
        oauthConsumerSecret: String,
        accessToken: String?,
        accessTokenSecret: String?,
        path: String
    ): String {

        val oauthNonce = UUID.randomUUID().toString().replace("-", "")
        val oauthSignatureMethod = "HMAC-SHA1"
        val oauthTimestamp = (System.currentTimeMillis() / 1000).toString()
        val oauthVersion = "1.0"

        val additionalParameters = StringBuilder()

        if (accessToken.isNullOrBlank()) {
            additionalParameters.append(HttpAuthHeader.Parameters.OAuthVersion + "=$oauthVersion")
        } else {
            additionalParameters.append(HttpAuthHeader.Parameters.OAuthToken + "=$accessToken&")
            additionalParameters.append(HttpAuthHeader.Parameters.OAuthVersion + "=$oauthVersion")
        }

        val oauthBuilder =
            HttpAuthHeader.Parameters.OAuthConsumerKey + "=$oauthConsumerKey&" +
                    HttpAuthHeader.Parameters.OAuthNonce + "=$oauthNonce&" +
                    HttpAuthHeader.Parameters.OAuthSignatureMethod + "=$oauthSignatureMethod&" +
                    HttpAuthHeader.Parameters.OAuthTimestamp + "=$oauthTimestamp&" +
                    additionalParameters.toString()

        val urlEncodedOAuthString = URLEncoder.encode(oauthBuilder, "UTF-8")
        val requestTokenApi = URLEncoder.encode("$BASEURL/$path", "UTF-8")
        val baseString = "$requestMethod&$requestTokenApi&$urlEncodedOAuthString"
        val oauthSig =
            createSignature(
                baseString,
                "$oauthConsumerSecret&" + if (accessTokenSecret.isNullOrBlank()) "" else accessTokenSecret
            )
        val urlEncodedOAuthSig = URLEncoder.encode(oauthSig, "UTF-8")

        return "OAuth ${HttpAuthHeader.Parameters.OAuthConsumerKey}=${'"'}$oauthConsumerKey${'"'}" +
                ",${HttpAuthHeader.Parameters.OAuthSignatureMethod}=${'"'}$oauthSignatureMethod${'"'}" +
                ",${HttpAuthHeader.Parameters.OAuthTimestamp}=${'"'}$oauthTimestamp${'"'}" +
                ",${HttpAuthHeader.Parameters.OAuthNonce}=${'"'}$oauthNonce${'"'}" +
                ",${HttpAuthHeader.Parameters.OAuthSignature}=${'"'}$urlEncodedOAuthSig${'"'}" +
                if (accessToken.isNullOrBlank()) ",${HttpAuthHeader.Parameters.OAuthVersion}=${'"'}$oauthVersion${'"'}"
                else
                    ",${HttpAuthHeader.Parameters.OAuthToken}=${'"'}$accessToken${'"'}" +
                            ",${HttpAuthHeader.Parameters.OAuthVersion}=${'"'}$oauthVersion${'"'}"

    }

}

fun createSignature(data: String, key: String): String {
    val mac = Mac.getInstance("HmacSHA1") //"HmacSHA256")
    val secretKey = SecretKeySpec(key.toByteArray(), "HmacSHA1")
    mac.init(secretKey)

    return Base64.getEncoder().encodeToString(mac.doFinal(data.toByteArray())).trim()
}