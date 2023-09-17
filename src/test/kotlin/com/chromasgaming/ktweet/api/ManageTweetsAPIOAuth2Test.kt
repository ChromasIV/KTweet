package com.chromasgaming.ktweet.api

import com.chromasgaming.ktweet.models.ManageTweets
import com.chromasgaming.ktweet.models.Tweet
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalSerializationApi
internal class ManageTweetsAPIOAuth2Test {
    private lateinit var manageTweets: TwitterManageTweetsApi
    private lateinit var post: ManageTweets

    @BeforeEach
    fun setUp() {
        manageTweets = TwitterManageTweetsApi("Bearer ${System.getProperty("oauth2AccessToken")}")
    }

    @Test
    fun createTweet() = runTest {
        val tweet = Tweet("#KTweet Testing on X")
        post = manageTweets.create(tweet)

        deleteTweet(post.data.id!!)
    }

    @Test
    fun createTweetReply() = runTest {
        val tweet = Tweet("#KTweet Testing on X", null, Tweet.Reply(null, "1702867177559236715"))
        post = manageTweets.create(tweet)
        deleteTweet(post.data.id!!)
    }

    /** Obtain a Media ID at https://studio.twitter.com/library [Tweet.Media.mediaIds]*/
    @Test
    fun createMediaTweet(): Unit = runTest {
        val tweet = Tweet("Tweeting with media!", Tweet.Media(listOf("1449722748268425225")))
        post = manageTweets.create(tweet)

        deleteTweet(post.data.id!!)
    }

    /**
     * Obtain a Media ID at https://studio.twitter.com/library [Tweet.Media.mediaIds]
     * Obtain an User ID at https://tweeterid.com [Tweet.Media.taggedUserIds]
     */
    @Test
    fun createMediaAndPhotoTagTweet(): Unit = runTest {
        val tweet = Tweet(
            "Tweeting with media!",
            Tweet.Media(listOf("1438588245340741640"), listOf("850887380739510275"))
        )
        post = manageTweets.create(tweet)

        deleteTweet(post.data.id!!)
    }

    @Test
    fun createDeleteTweet(): Unit = runTest {
        val tweet = Tweet("Tweeting to delete")
        post = manageTweets.create(tweet)
        deleteTweet(post.data.id!!)
    }

    private fun deleteTweet(id: String): Unit = runTest {
        post = manageTweets.destroy(id)
    }
}
