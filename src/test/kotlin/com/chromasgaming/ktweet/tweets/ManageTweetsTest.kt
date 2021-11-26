package com.chromasgaming.ktweet.tweets

import com.chromasgaming.ktweet.dtos.ManageTweetsDTO
import com.chromasgaming.ktweet.dtos.Tweet
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalSerializationApi
internal class ManageTweetsTest {
    private val json = Json { encodeDefaults = false }
    private lateinit var manageTweets: ManageTweets
    private lateinit var post: ManageTweetsDTO

    @BeforeEach
    fun setUp() {
        manageTweets = ManageTweets()
    }

    @Test
    fun createTweet() = runBlocking {
        val tweet = Tweet("Tweet Tweet #Kotlin!")
        post = manageTweets.create(tweet)
        post = manageTweets.destroy(post.data.id!!)
    }

    /** Obtain a Media ID at https://studio.twitter.com/library [Tweet.Media.media_ids]*/
    @Test
    fun createMediaTweet() = runBlocking {
        val tweet = Tweet("Tweeting with media!", Tweet.Media(listOf("1449722748268425225")))
        post = manageTweets.create(tweet)
        manageTweets.destroy(post.data.id!!)
    }

    /**
     * Obtain a Media ID at https://studio.twitter.com/library [Tweet.Media.media_ids]
     * Obtain an User ID at https://tweeterid.com [Tweet.Media.tagged_user_ids]
     */
    @Test
    fun createMediaAndPhotoTagTweet() = runBlocking {
        val tweet = Tweet(
            "Tweeting with media!",
            Tweet.Media(listOf("1438588245340741640"), listOf("850887380739510275"))
        )
        post = manageTweets.create(tweet)
        manageTweets.destroy(post.data.id!!)
    }

    @Test
    fun createDeleteTweet() = runBlocking {
        val tweet = Tweet("Tweeting to delete")
        post = manageTweets.create(tweet)
        manageTweets.destroy(post.data.id!!)
    }


}