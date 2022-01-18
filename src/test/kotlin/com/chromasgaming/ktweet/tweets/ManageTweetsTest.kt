package com.chromasgaming.ktweet.tweets

import com.chromasgaming.ktweet.constants.VERSION
import com.chromasgaming.ktweet.dtos.ManageTweetsDTO
import com.chromasgaming.ktweet.dtos.TweetDTO
import com.chromasgaming.ktweet.oauth.SignatureBuilder
import com.chromasgaming.ktweet.oauth.buildSignature
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalSerializationApi
internal class ManageTweetsTest {
    private lateinit var manageTweets: ManageTweets
    private lateinit var post: ManageTweetsDTO

    private lateinit var authorizationHeaderString: String
    private lateinit var signatureBuilder: SignatureBuilder


    @BeforeEach
    fun setUp() {
        manageTweets = ManageTweets()

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
        val tweetDTO = TweetDTO("Tweet Tweet #Kotlin!")
        post = manageTweets.create(tweetDTO, authorizationHeaderString)

        val authorizationHeaderDeleteString = buildSignature(
            "DELETE",
            signatureBuilder,
            "$VERSION/tweets/${post.data.id!!}",
            emptyMap()
        )
        post = manageTweets.destroy(post.data.id!!, authorizationHeaderDeleteString)
    }

    /** Obtain a Media ID at https://studio.twitter.com/library [TweetDTO.Media.media_ids]*/
    @Test
    fun createMediaTweet(): Unit = runBlocking {
        val tweetDTO = TweetDTO("Tweeting with media!", TweetDTO.Media(listOf("1449722748268425225")))
        post = manageTweets.create(tweetDTO, authorizationHeaderString)

        val authorizationHeaderDeleteString = buildSignature(
            "DELETE",
            signatureBuilder,
            "$VERSION/tweets/${post.data.id!!}",
            emptyMap()
        )
        post = manageTweets.destroy(post.data.id!!, authorizationHeaderDeleteString)
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
        post = manageTweets.create(tweetDTO, authorizationHeaderString)

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
        val tweetDTO = TweetDTO("Tweeting to delete")
        post = manageTweets.create(tweetDTO, authorizationHeaderString)

        val authorizationHeaderDeleteString = buildSignature(
            "DELETE",
            signatureBuilder,
            "$VERSION/tweets/${post.data.id!!}",
            emptyMap()
        )
        post = manageTweets.destroy(post.data.id!!, authorizationHeaderDeleteString)
    }
}
