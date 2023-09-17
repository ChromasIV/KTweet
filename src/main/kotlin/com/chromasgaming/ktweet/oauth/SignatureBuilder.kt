//package com.chromasgaming.ktweet.oauth
//
//import com.chromasgaming.ktweet.util.BASEURL
//import com.chromasgaming.ktweet.util.MILLISECONDS
//import io.ktor.http.auth.HttpAuthHeader
//import java.net.URLEncoder
//import java.util.*
//import javax.crypto.Mac
//import javax.crypto.spec.SecretKeySpec
//
///**
// * This class handles building the Authentication signature needed for OAuth 1.0a User context.
// * Reference [Authentication](https://developer.twitter.com/en/docs/authentication/oauth-1-0a/creating-a-signature)
// */
//class SignatureBuilder private constructor(
//    val oauthConsumerKey: String,
//    val oauthConsumerSecret: String,
//    val accessToken: String,
//    val accessTokenSecret: String
//) {
//
//    data class Builder(
//        private var oauthConsumerKey: String = "",
//        private var oauthConsumerSecret: String = "",
//        private var accessToken: String = "",
//        private var accessTokenSecret: String = ""
//    ) {
//        fun oauthConsumerKey(oauthConsumerKey: String) = apply { this.oauthConsumerKey = oauthConsumerKey }
//        fun oauthConsumerSecret(oauthConsumerSecret: String) = apply { this.oauthConsumerSecret = oauthConsumerSecret }
//        fun accessToken(accessToken: String) = apply { this.accessToken = accessToken }
//        fun accessTokenSecret(accessTokenSecret: String) = apply { this.accessTokenSecret = accessTokenSecret }
//        fun build() = SignatureBuilder(oauthConsumerKey, oauthConsumerSecret, accessToken, accessTokenSecret)
//    }
//}
//
//
//fun buildSignature(
//    requestMethod: String,
//    signatureBuilder: SignatureBuilder,
//    path: String,
//    paramMap: Map<String, String>
//): String {
//
//    val oauthNonce = UUID.randomUUID().toString().replace("-", "")
//    val oauthSignatureMethod = "HMAC-SHA1"
//    val oauthTimestamp = (System.currentTimeMillis() / MILLISECONDS).toString()
//    val oauthVersion = "1.0"
//
//    val additionalParameters = mutableListOf<String>().apply {
//        if (signatureBuilder.accessToken.isNotEmpty()) {
//            add("${HttpAuthHeader.Parameters.OAuthToken}=${signatureBuilder.accessToken}")
//        }
//        add("${HttpAuthHeader.Parameters.OAuthVersion}=$oauthVersion")
//    }
//
//    val oauthBuilder = (
//            listOf(
//                "${HttpAuthHeader.Parameters.OAuthConsumerKey}=${signatureBuilder.oauthConsumerKey}",
//                "${HttpAuthHeader.Parameters.OAuthNonce}=$oauthNonce",
//                "${HttpAuthHeader.Parameters.OAuthSignatureMethod}=$oauthSignatureMethod",
//                "${HttpAuthHeader.Parameters.OAuthTimestamp}=$oauthTimestamp"
//            ) + additionalParameters + paramMap.map { "${it.key}=${it.value}" }
//            ).joinToString("&")
//
//
//    val urlEncodedOAuthString = URLEncoder.encode(oauthBuilder, "UTF-8")
//    val requestTokenApi = URLEncoder.encode("$BASEURL/$path", "UTF-8")
//    val baseString = "$requestMethod&$requestTokenApi&$urlEncodedOAuthString"
//
//    val oauthSig =
//        createSignatureBase64(
//            baseString,
//            "${signatureBuilder.oauthConsumerSecret}&${signatureBuilder.accessTokenSecret}"
//        )
//    val urlEncodedOAuthSig = URLEncoder.encode(oauthSig, "UTF-8")
//
//    return "OAuth " + (
//            listOf(
//                "${HttpAuthHeader.Parameters.OAuthConsumerKey}=\"${signatureBuilder.oauthConsumerKey}\"",
//                "${HttpAuthHeader.Parameters.OAuthSignatureMethod}=\"$oauthSignatureMethod\"",
//                "${HttpAuthHeader.Parameters.OAuthTimestamp}=\"$oauthTimestamp\"",
//                "${HttpAuthHeader.Parameters.OAuthNonce}=\"$oauthNonce\"",
//                "${HttpAuthHeader.Parameters.OAuthSignature}=\"$urlEncodedOAuthSig\""
//            ) + additionalParameters.map { "${it.substringBefore('=')}=\"${it.substringAfter('=')}\"" }
//            ).joinToString(",")
//}
//
//private fun createSignatureBase64(data: String, key: String): String {
//    val mac = Mac.getInstance("HmacSHA1") //"HmacSHA256")
//    val secretKey = SecretKeySpec(key.toByteArray(), "HmacSHA1")
//    mac.init(secretKey)
//
//    return Base64.getEncoder().encodeToString(mac.doFinal(data.toByteArray())).trim()
//
//}
