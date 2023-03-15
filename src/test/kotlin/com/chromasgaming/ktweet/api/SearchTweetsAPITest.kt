package com.chromasgaming.ktweet.api

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.models.TweetObject
import com.chromasgaming.ktweet.oauth.SignatureBuilder
import com.chromasgaming.ktweet.oauth.buildSignature
import io.ktor.serialization.JsonConvertException
import java.net.URLEncoder
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@ExperimentalSerializationApi
internal class SearchTweetsAPITest {
    private lateinit var searchTweetsAPI: SearchTweetsAPI
    private lateinit var signatureBuilder: SignatureBuilder

    private val clientConfig = ClientConfig()

    @BeforeEach
    fun setUp() {
        signatureBuilder = SignatureBuilder.Builder()
            .oauthConsumerKey(System.getProperty("consumerKey"))
            .oauthConsumerSecret(System.getProperty("consumerSecret"))
            .accessToken(System.getProperty("accessToken"))
            .accessTokenSecret(System.getProperty("accessTokenSecret"))
            .build()

        searchTweetsAPI = SearchTweetsAPI(clientConfig)
    }

    @Test
    fun searchTweets() = runTest {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "cat"
        paramMap["tweet.fields"] =
            "author_id"

        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweetsAPI.searchTweet(paramMap, authorizationHeaderString)
        assertTrue(tweetObject.isNotEmpty())
    }

    @Test
    fun searchTweets_NoResults() = runTest {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "from:test"
        paramMap["tweet.fields"] =
            "author_id"

        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        assertThrows<JsonConvertException>("No data available.") {
            val tweetObject: List<TweetObject> = searchTweetsAPI.searchTweet(paramMap, authorizationHeaderString)
        }
    }

    @Test
    fun searchTweets_hasMedia() = runTest {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "(from:chromasiv) has:media -is:retweet"
        paramMap["tweet.fields"] =
            "created_at,attachments"

        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweetsAPI.searchTweet(paramMap, authorizationHeaderString)
        assertTrue(tweetObject.isNotEmpty())

        for (tweet in tweetObject) {
            val attachments = tweet.attachments
            assertNotNull(attachments)

            val mediaKeys = attachments.media_keys
            assertNotNull(mediaKeys)
            assertTrue(mediaKeys.isNotEmpty())
        }
    }

    @Test
    fun searchTweets_details() = runTest {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "kotlin -is:retweet"
        paramMap["tweet.fields"] =
            "author_id,context_annotations,entities"
        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweetsAPI.searchTweet(paramMap, authorizationHeaderString)
        assertTrue(tweetObject.isNotEmpty())
    }

    @Test
    fun searchTweets_geo() = runTest {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "from:chromasiv -is:retweet"
        paramMap["tweet.fields"] =
            "geo"
        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweetsAPI.searchTweet(paramMap, authorizationHeaderString)
        assertTrue(tweetObject.isNotEmpty())
    }

    @Test
    fun searchTweets_metrics() = runTest {
        val paramMap = LinkedHashMap<String, String>()
        //Must be you for restricted information
        paramMap["query"] = "from:chromasiv"
        paramMap["tweet.fields"] = "non_public_metrics,public_metrics,organic_metrics"
        //Uncomment if you have promoted metrics
        //paramMap["tweet.fields"] = "non_public_metrics,public_metrics,organic_metrics,promoted_metrics"
        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweetsAPI.searchTweet(paramMap, authorizationHeaderString)
        assertTrue(tweetObject.isNotEmpty())
    }

    @Test
    fun searchTweets_reference() = runTest {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "kotlin"
        paramMap["tweet.fields"] = "in_reply_to_user_id,referenced_tweets"
        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }
        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweetsAPI.searchTweet(paramMap, authorizationHeaderString)
        assertTrue(tweetObject.isNotEmpty())
    }

    @Test
    fun searchTweets_others() = runTest {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "(from:chromasiv)"
        paramMap["tweet.fields"] = "reply_settings"
        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweetsAPI.searchTweet(paramMap, authorizationHeaderString)
        assertTrue(tweetObject.isNotEmpty())

        for (tweet in tweetObject) {
            val replySetting = tweet.reply_settings
            assertNotNull(replySetting)
        }
    }

}
