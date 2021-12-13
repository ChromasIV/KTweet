package com.chromasgaming.ktweet.tweets

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalSerializationApi
internal class SearchTweetsTest {
    private lateinit var searchTweets: SearchTweets

    @BeforeEach
    fun setUp() {
        searchTweets = SearchTweets()
    }

    @Test
    fun searchTweets(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "from:chromasiv -is:retweet"
        paramMap["tweet.fields"] =
            "geo,non_public_metrics,public_metrics,organic_metrics,possibly_sensitive"
        searchTweets.search(paramMap)
    }

}
