package com.chromasgaming.ktweet.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManageTweets(val data: Data) {
    @Serializable
    data class Data(val id: String? = null, val text: String? = null, val deleted: Boolean? = null)
}

@Serializable
data class Tweet(val text: String, val media: Media? = null, val reply: Reply? = null) {

    @Serializable
    data class Media(@SerialName("media_ids") val mediaIds: List<String>? = null, @SerialName("tagged_user_ids") val taggedUserIds: List<String>? = null)

    @Serializable
    data class Reply(@SerialName("exclude_reply_user_ids") val excludeReplyUserIds: List<String>? = null, @SerialName("in_reply_to_tweet_id") val inReplyToTweetId: String? = null)
}
