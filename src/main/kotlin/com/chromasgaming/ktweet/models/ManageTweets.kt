package com.chromasgaming.ktweet.models

import kotlinx.serialization.Serializable

@Serializable
data class ManageTweets(val data: Data) {
    @Serializable
    data class Data(val id: String? = null, val text: String? = null, val deleted: Boolean? = null)
}

@Serializable
data class Tweet(val text: String, val media: Media? = null, val reply: Reply? = null) {

    @Serializable
    data class Media(val media_ids: List<String>? = null, val tagged_user_ids: List<String>? = null)

    @Serializable
    data class Reply(val exclude_reply_user_ids: List<String>? = null, val in_reply_to_tweet_id: String? = null)
}
