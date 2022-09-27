package com.chromasgaming.ktweet.api

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
    private lateinit var manageTweets: ManageTweetsAPI
    private lateinit var post: ManageTweets

    private lateinit var authorizationHeaderString: String
    private lateinit var signatureBuilder: SignatureBuilder


    @BeforeEach
    fun setUp() {
        manageTweets = ManageTweetsAPI()

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

    }

    @Test
    fun createTweet() = runBlocking {
        val tweet = Tweet("Tweet Tweet #Kotlin!")
        post = manageTweets.create(tweet, authorizationHeaderString)

        val authorizationHeaderDeleteString = buildSignature(
            "DELETE",
            signatureBuilder,
            "$VERSION/tweets/${post.data.id!!}",
            emptyMap()
        )
        post = manageTweets.destroy(post.data.id!!, authorizationHeaderDeleteString)
    }

    @Test
    fun createTweetReply() = runBlocking {
        val tweet = Tweet("Tweet #Kotlin!", null, Tweet.Reply(null, "1465160399976747012"))
        post = manageTweets.create(tweet, authorizationHeaderString)

        val authorizationHeaderDeleteString = buildSignature(
            "DELETE",
            signatureBuilder,
            "$VERSION/tweets/${post.data.id!!}",
            emptyMap()
        )
        post = manageTweets.destroy(post.data.id!!, authorizationHeaderDeleteString)
    }

    /** Obtain a Media ID at https://studio.twitter.com/library [Tweet.Media.media_ids]*/
    @Test
    fun createMediaTweet(): Unit = runBlocking {
        val tweet = Tweet("Tweeting with media!", Tweet.Media(listOf("1449722748268425225")))
        post = manageTweets.create(tweet, authorizationHeaderString)

        val authorizationHeaderDeleteString = buildSignature(
            "DELETE",
            signatureBuilder,
            "$VERSION/tweets/${post.data.id!!}",
            emptyMap()
        )
        post = manageTweets.destroy(post.data.id!!, authorizationHeaderDeleteString)
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
        post = manageTweets.create(tweet, authorizationHeaderString)

        val authorizationHeaderDeleteString = buildSignature(
            "DELETE",
            signatureBuilder,
            "$VERSION/tweets/${post.data.id!!}",
            emptyMap()
        )
        post = manageTweets.destroy(post.data.id!!, authorizationHeaderDeleteString)
    }

    @Test
    fun createDeleteTweet(): Unit = runBlocking {
        val tweet = Tweet("Tweeting to delete")
        post = manageTweets.create(tweet, authorizationHeaderString)

        val authorizationHeaderDeleteString = buildSignature(
            "DELETE",
            signatureBuilder,
            "$VERSION/tweets/${post.data.id!!}",
            emptyMap()
        )
        post = manageTweets.destroy(post.data.id!!, authorizationHeaderDeleteString)
    }
}
