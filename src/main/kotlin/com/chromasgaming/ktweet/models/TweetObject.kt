package com.chromasgaming.ktweet.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject


// reference: https://developer.twitter.com/en/docs/twitter-api/data-dictionary/object-model/tweet
@Serializable
data class TweetObject(
    val id: String,
    val text: String,
    @SerialName("edit_history_tweet_ids") val editHistoryTweetIds: List<String>,
    val attachments: Attachments? = null,
    val author_id: String? = null,
    val context_annotations: List<JsonObject>? = null,
    val conversation_id: String? = null,
    @Contextual val created_at: String? = null,
    val entities: JsonObject? = null,
    val geo: Geo? = null,
    val in_reply_to_user_id: String? = null,
    val lang: String? = null,
    val non_public_metrics: NonPublicMetrics? = null,
    val organic_metrics: OrganicMetrics? = null,
    val possibly_sensitive: Boolean? = null,
    val promoted_metrics: PromotedMetrics? = null,
    val public_metrics: PublicMetrics? = null,
    val referenced_tweets: ArrayList<JsonObject>? = null,
    val reply_settings: String? = null,
    val source: String? = null,
    val withheld: WithHolding? = null

)

@Serializable
data class Attachments(val poll_ids: ArrayList<String>? = null, val media_keys: ArrayList<String>? = null)

@Serializable
data class Geo(val coordinates: Coordinates? = null, val place_id: String? = null)

@Serializable
data class Coordinates(val type: String? = null, val coordinates: ArrayList<Float>? = null)

@Serializable
data class NonPublicMetrics(
    val impression_count: Int? = null,
    val url_link_clicks: Int? = null,
    val user_profile_clicks: Int? = null
)

@Serializable
data class OrganicMetrics(
    val impression_count: Int? = null,
    val like_count: Int? = null,
    val reply_count: Int? = null,
    val retweet_count: Int? = null,
    val url_link_clicks: Int? = null,
    val user_profile_clicks: Int? = null
)

@Serializable
data class PromotedMetrics(
    val impression_count: Int? = null,
    val like_count: Int? = null,
    val reply_count: Int? = null,
    val retweet_count: Int? = null,
    val url_link_clicks: Int? = null,
    val user_profile_clicks: Int? = null
)

@Serializable
data class PublicMetrics(
    val retweet_count: Int? = null,
    val reply_count: Int? = null,
    val like_count: Int? = null,
    val quote_count: Int? = null
)

@Serializable
data class WithHolding(val copyright: Boolean? = null, val country_codes: ArrayList<String>? = null)
