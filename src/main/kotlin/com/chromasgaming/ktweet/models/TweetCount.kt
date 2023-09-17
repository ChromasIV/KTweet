package com.chromasgaming.ktweet.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TweetCount(val data: List<Data>, val meta: Meta) {

    @Serializable
    data class Data(val end: String, val start: String, @SerialName("tweet_count") val tweetCount: Int)

    @Serializable
    data class Meta(@SerialName("total_tweet_count") val totalTweetCount: Int)
}

enum class Granularity(val value: String) {
    DAY("day"),
    HOUR("hour"),
    MINUTE("minute");
}
