package com.chromasgaming.ktweet.models

import kotlinx.serialization.Serializable

@Serializable
data class TweetCount(val data: List<Data>, val meta: Meta) {

    @Serializable
    data class Data(val end: String, val start: String, val tweet_count: Int)

    @Serializable
    data class Meta(val total_tweet_count: Int)
}

enum class Granularity(val value: String) {
    DAY("day"),
    HOUR("hour"),
    MINUTE("minute");
}
