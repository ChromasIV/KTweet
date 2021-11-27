package com.chromasgaming.ktweet.tweets

import com.chromasgaming.ktweet.dtos.ManageTweetsDTO
import com.chromasgaming.ktweet.dtos.TweetDTO
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalSerializationApi
internal class ManageTweetsTest {
    private lateinit var manageTweets: ManageTweets
    private lateinit var post: ManageTweetsDTO

    @BeforeEach
    fun setUp() {
        manageTweets = ManageTweets()
    }

    @Test
    fun createTweet() = runBlocking {
        val tweetDTO = TweetDTO("Tweet Tweet #Kotlin!")
        post = manageTweets.create(tweetDTO)
        post = manageTweets.destroy(post.data.id!!)
    }

    /** Obtain a Media ID at https://studio.twitter.com/library [TweetDTO.Media.media_ids]*/
    @Test
    fun createMediaTweet(): Unit = runBlocking {
        val tweetDTO = TweetDTO("Tweeting with media!", TweetDTO.Media(listOf("1449722748268425225")))
        post = manageTweets.create(tweetDTO)
        manageTweets.destroy(post.data.id!!)
    }

    /**
     * Obtain a Media ID at https://studio.twitter.com/library [TweetDTO.Media.media_ids]
     * Obtain an User ID at https://tweeterid.com [TweetDTO.Media.tagged_user_ids]
     */
    @Test
    fun createMediaAndPhotoTagTweet(): Unit = runBlocking {
        val tweetDTO = TweetDTO(
            "Tweeting with media!",
            TweetDTO.Media(listOf("1438588245340741640"), listOf("850887380739510275"))
        )
        post = manageTweets.create(tweetDTO)
        manageTweets.destroy(post.data.id!!)
    }

    @Test
    fun createDeleteTweet(): Unit = runBlocking {
        val tweetDTO = TweetDTO("Tweeting to delete")
        post = manageTweets.create(tweetDTO)
        manageTweets.destroy(post.data.id!!)
    }


}