package com.chromasgaming.ktweet.tweets

import com.chromasgaming.ktweet.oauth2.TwitterOauth2Authentication
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CountTweetsTest {
    private lateinit var countTweets: CountTweets
    private val json = Json { encodeDefaults = false }


    @BeforeEach
    fun setUp() {
        countTweets = CountTweets()
    }

    @Test
    fun recentTweets(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "from:ChromasIV"
        paramMap["granularity"] = Granularity.day.name


        val bearerToken = TwitterOauth2Authentication().getAppOnlyBearerToken(
            System.getProperty("consumerKey"),
            System.getProperty("consumerSecret")
        )

        println(countTweets.recent(paramMap, bearerToken))
    }
}