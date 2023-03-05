package com.chromasgaming.ktweet.api

import com.chromasgaming.ktweet.config.ClientConfig
import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.models.ManageTweets
import com.chromasgaming.ktweet.models.Tweet
import com.chromasgaming.ktweet.oauth.SignatureBuilder
import com.chromasgaming.ktweet.oauth.buildSignature
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalSerializationApi
internal class ManageTweetsAPITest {
    private lateinit var manageTweets: TwitterManageTweetsApi
    private lateinit var post: ManageTweets

    private lateinit var authorizationHeaderString: String
    private lateinit var signatureBuilder: SignatureBuilder

    private val clientConfig = ClientConfig()

    @BeforeEach
    fun setUp() {
        signatureBuilder = SignatureBuilder.Builder()
            .oauthConsumerKey(System.getProperty("consumerKey"))
            .oauthConsumerSecret(System.getProperty("consumerSecret"))
            .accessToken(System.getProperty("accessToken"))
            .accessTokenSecret(System.getProperty("accessTokenSecret"))
            .build()

        authorizationHeaderString = buildSignature(
            "POST",
            signatureBuilder,
            "$VERSION/tweets",
            emptyMap()
        )

        manageTweets = TwitterManageTweetsApi(clientConfig, authorizationHeaderString)

    }

    @Test
    fun createTweet() = runBlocking {
        val tweet = Tweet("Tweet Tweet #Kotlin!")
        post = manageTweets.create(tweet)

        deleteTweet(post.data.id!!)
    }

    @Test
    fun createTweetReply() = runBlocking {
        val tweet = Tweet("Tweet #Kotlin!", null, Tweet.Reply(null, "1465160399976747012"))
        post = manageTweets.create(tweet)
        deleteTweet(post.data.id!!)
    }

    /** Obtain a Media ID at https://studio.twitter.com/library [Tweet.Media.media_ids]*/
    @Test
    fun createMediaTweet(): Unit = runBlocking {
        val tweet = Tweet("Tweeting with media!", Tweet.Media(listOf("1449722748268425225")))
        post = manageTweets.create(tweet)

        deleteTweet(post.data.id!!)
    }

    /**
     * Obtain a Media ID at https://studio.twitter.com/library [Tweet.Media.media_ids]
     * Obtain an User ID at https://tweeterid.com [Tweet.Media.tagged_user_ids]
     */
    @Test
    fun createMediaAndPhotoTagTweet(): Unit = runBlocking {
        val tweet = Tweet(
            "Tweeting with media!",
            Tweet.Media(listOf("1438588245340741640"), listOf("850887380739510275"))
        )
        post = manageTweets.create(tweet)

        deleteTweet(post.data.id!!)
    }

    @Test
    fun createDeleteTweet(): Unit = runBlocking {
        val tweet = Tweet("Tweeting to delete")
        post = manageTweets.create(tweet)
        deleteTweet(post.data.id!!)
    }

    private fun deleteTweet(id: String) : Unit = runBlocking {
        val authorizationHeaderDeleteString = buildSignature(
            "DELETE",
            signatureBuilder,
            "$VERSION/tweets/${post.data.id!!}",
            emptyMap()
        )
        manageTweets = TwitterManageTweetsApi(clientConfig, authorizationHeaderDeleteString)
        post = manageTweets.destroy(post.data.id!!)
    }
}
