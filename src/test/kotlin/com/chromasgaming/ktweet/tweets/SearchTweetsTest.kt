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
        paramMap["query"] = "from:chromasiv -is:retweet"
        paramMap["tweet.fields"] =
            "created_at,attachments"
        val tweetObject = searchTweets.search(paramMap)
        //print(json.decodeFromString<JsonObject>(searchTweets.search(paramMap))["data"])
        print(tweetObject)
    }

}
