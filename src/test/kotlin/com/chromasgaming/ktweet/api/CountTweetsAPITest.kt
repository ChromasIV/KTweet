package com.chromasgaming.ktweet.api

import com.chromasgaming.ktweet.models.Granularity
import com.chromasgaming.ktweet.oauth2.TwitterOauth2Authentication
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CountTweetsAPITest {
    private lateinit var countTweetsAPI: CountTweetsAPI


    @BeforeEach
    fun setUp() {
        countTweetsAPI = CountTweetsAPI()
    }

    @Test
    fun recentTweets(): Unit = runBlocking {
        val paramMap = LinkedHashMap<String, String>()
        paramMap["query"] = "from:250181762"
        paramMap["granularity"] = Granularity.DAY.value

        val bearerToken = TwitterOauth2Authentication().getAppOnlyBearerToken(
            System.getProperty("consumerKey"),
            System.getProperty("consumerSecret")
        )

        val tweetCount = countTweetsAPI.recent(paramMap, bearerToken)

        assert(tweetCount.data.isNotEmpty())
        assert(tweetCount.meta.total_tweet_count >= 0)
    }
}
