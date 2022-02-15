package com.chromasgaming.ktweet.tweets

import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.dtos.TweetObject
import com.chromasgaming.ktweet.oauth.SignatureBuilder
import com.chromasgaming.ktweet.oauth.buildSignature
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.net.URLEncoder

@ExperimentalSerializationApi
internal class SearchTweetsTest {
    private lateinit var searchTweets: SearchTweets
    private val json = Json { encodeDefaults = false }

    @BeforeEach
    fun setUp() {
        searchTweets = SearchTweets()
    }

    @Test
    fun searchTweets(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "kotlin"
        paramMap["tweet.fields"] =
            "author_id"

        val signatureBuilder = SignatureBuilder.Builder()
            .oauthConsumerKey(System.getProperty("consumerKey"))
            .oauthConsumerSecret(System.getProperty("consumerSecret"))
            .accessToken(System.getProperty("accessToken"))
            .accessTokenSecret(System.getProperty("accessTokenSecret"))
            .build()

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweets.search(paramMap, authorizationHeaderString)
        tweetObject.forEach {
            println(json.encodeToString(it))
        }
    }

    @Test
    fun searchTweets_NoResults(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "from:test"
        paramMap["tweet.fields"] =
            "author_id"

        val signatureBuilder = SignatureBuilder.Builder()
            .oauthConsumerKey(System.getProperty("consumerKey"))
            .oauthConsumerSecret(System.getProperty("consumerSecret"))
            .accessToken(System.getProperty("accessToken"))
            .accessTokenSecret(System.getProperty("accessTokenSecret"))
            .build()

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweets.search(paramMap, authorizationHeaderString)
        assert(tweetObject.isEmpty())
    }

    @Test
    fun searchTweets_hasMedia(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "kotlin has:media"
        paramMap["tweet.fields"] =
            "created_at,attachments"

        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val signatureBuilder = SignatureBuilder.Builder()
            .oauthConsumerKey(System.getProperty("consumerKey"))
            .oauthConsumerSecret(System.getProperty("consumerSecret"))
            .accessToken(System.getProperty("accessToken"))
            .accessTokenSecret(System.getProperty("accessTokenSecret"))
            .build()

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweets.search(paramMap, authorizationHeaderString)
        tweetObject.forEach {
            println(json.encodeToString(it))
        }
    }

    @Test
    fun searchTweets_details(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "kotlin -is:retweet"
        paramMap["tweet.fields"] =
            "author_id,context_annotations,entities"
        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val signatureBuilder = SignatureBuilder.Builder()
            .oauthConsumerKey(System.getProperty("consumerKey"))
            .oauthConsumerSecret(System.getProperty("consumerSecret"))
            .accessToken(System.getProperty("accessToken"))
            .accessTokenSecret(System.getProperty("accessTokenSecret"))
            .build()

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweets.search(paramMap, authorizationHeaderString)
        print(tweetObject)
    }

    @Test
    fun searchTweets_geo(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "from:chromasiv -is:retweet"
        paramMap["tweet.fields"] =
            "geo"
        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val signatureBuilder = SignatureBuilder.Builder()
            .oauthConsumerKey(System.getProperty("consumerKey"))
            .oauthConsumerSecret(System.getProperty("consumerSecret"))
            .accessToken(System.getProperty("accessToken"))
            .accessTokenSecret(System.getProperty("accessTokenSecret"))
            .build()

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweets.search(paramMap, authorizationHeaderString)
        print(tweetObject)
    }

    @Test
    fun searchTweets_metrics(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        //Must be you for restricted information
        paramMap["query"] = "from:chromasiv"
        paramMap["tweet.fields"] = "non_public_metrics,public_metrics,organic_metrics"
        //Uncomment if you have promoted metrics
        //paramMap["tweet.fields"] = "non_public_metrics,public_metrics,organic_metrics,promoted_metrics"
        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val signatureBuilder = SignatureBuilder.Builder()
            .oauthConsumerKey(System.getProperty("consumerKey"))
            .oauthConsumerSecret(System.getProperty("consumerSecret"))
            .accessToken(System.getProperty("accessToken"))
            .accessTokenSecret(System.getProperty("accessTokenSecret"))
            .build()

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweets.search(paramMap, authorizationHeaderString)
        print(tweetObject)
    }

    @Test
    fun searchTweets_reference(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "kotlin"
        paramMap["tweet.fields"] = "in_reply_to_user_id,referenced_tweets"
        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val signatureBuilder = SignatureBuilder.Builder()
            .oauthConsumerKey(System.getProperty("consumerKey"))
            .oauthConsumerSecret(System.getProperty("consumerSecret"))
            .accessToken(System.getProperty("accessToken"))
            .accessTokenSecret(System.getProperty("accessTokenSecret"))
            .build()

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweets.search(paramMap, authorizationHeaderString)
        print(tweetObject)
    }

    @Test
    fun searchTweets_others(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "kotlin"
        paramMap["tweet.fields"] = "reply_settings,source,withheld"
        paramMap.forEach { (key, value) ->
            paramMap[key] = URLEncoder.encode(value, "UTF-8").replace("+", "%20")
        }

        val signatureBuilder = SignatureBuilder.Builder()
            .oauthConsumerKey(System.getProperty("consumerKey"))
            .oauthConsumerSecret(System.getProperty("consumerSecret"))
            .accessToken(System.getProperty("accessToken"))
            .accessTokenSecret(System.getProperty("accessTokenSecret"))
            .build()

        val authorizationHeaderString = buildSignature(
            "GET",
            signatureBuilder,
            "$VERSION/tweets/search/recent",
            paramMap
        )

        val tweetObject: List<TweetObject> = searchTweets.search(paramMap, authorizationHeaderString)
        print(tweetObject)
    }

}
