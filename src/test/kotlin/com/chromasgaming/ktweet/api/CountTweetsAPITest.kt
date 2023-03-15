package com.chromasgaming.ktweet.api

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.models.Granularity
import com.chromasgaming.ktweet.oauth2.TwitterOauth2Authentication
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CountTweetsAPITest {
    private lateinit var countTweetsAPI: CountTweetsAPI
    private val clientConfig = ClientConfig()

    @BeforeEach
    fun setUp() {
        countTweetsAPI = CountTweetsAPI(clientConfig)
    }

    @Test
    fun recentTweets() = runTest {
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
