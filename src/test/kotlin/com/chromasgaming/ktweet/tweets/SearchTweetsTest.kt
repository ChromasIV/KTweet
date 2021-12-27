package com.chromasgaming.ktweet.tweets

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

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
        paramMap["query"] = "from:chromasiv has:media"
        paramMap["tweet.fields"] =
            "created_at,attachments"
        val tweetObject = searchTweets.search(paramMap)
        print(tweetObject)
    }

    @Test
    fun searchTweets_details(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "from:chromasiv -is:retweet"
        paramMap["tweet.fields"] =
            "author_id,context_annotations,entities"
        val tweetObject = searchTweets.search(paramMap)
        print(tweetObject)
    }

    @Test
    fun searchTweets_geo(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "from:chromasiv -is:retweet"
        paramMap["tweet.fields"] =
            "geo"
        val tweetObject = searchTweets.search(paramMap)
        print(tweetObject)
    }

    @Test
    fun searchTweets_metrics(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "from:chromasiv"
        paramMap["tweet.fields"] = "non_public_metrics,public_metrics,organic_metrics"
        //Uncomment if you have promoted metrics
        //paramMap["tweet.fields"] = "non_public_metrics,public_metrics,organic_metrics,promoted_metrics"
        val tweetObject = searchTweets.search(paramMap)
        print(tweetObject)
    }

    @Test
    fun searchTweets_reference(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "from:chromasiv"
        paramMap["tweet.fields"] = "in_reply_to_user_id,referenced_tweets"
        val tweetObject = searchTweets.search(paramMap)
        print(tweetObject)
    }

    @Test
    fun searchTweets_others(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "from:chromasiv"
        paramMap["tweet.fields"] = "reply_settings,source,withheld"
        val tweetObject = searchTweets.search(paramMap)
        print(tweetObject)
    }

}
