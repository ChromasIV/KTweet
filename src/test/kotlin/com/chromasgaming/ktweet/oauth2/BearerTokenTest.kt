//package com.chromasgaming.ktweet.oauth2
//
//import kotlinx.coroutines.runBlocking
//import kotlin.test.Test
//
//internal class BearerTokenTest {
//
//    @Test
//    fun bearerToken() {
//        //Requires code from URL authorization for testing
//        /*
//            Step 1. Sign up for an account - https://developer.twitter.com/en/portal/dashboard
//            Step 2. Get client id
//            Step 3. Setup callback URL to be something local
//            Step 3. Craft the following URL and go to it in the browser.
//            Step 4. https://twitter.com/i/oauth2/authorize?response_type=code&client_id=INSERT_CLIENT_ID&redirect_uri=http://localhost:8080/callback&scope=tweet.read%20tweet.write%20users.read%20offline.access&state=state&code_challenge=challenge&code_challenge_method=plain
//
//         */
//
//        //https://twitter.com/i/oauth2/authorize?response_type=code&client_id=INSERT_CLIENT_ID&redirect_uri=http://localhost:8080/callback&scope=tweet.read%20tweet.write%20users.read%20offline.access&state=state&code_challenge=challenge&code_challenge_method=plain
//
//        runBlocking {
//            println(
//                TwitterOauth2Authentication().getBearerToken("", System.getProperty("clientId"), System.getProperty("clientSecret"), "http://127.0.0.1:8080/callback", "challenge")
//            )
//        }
//    }
//}
