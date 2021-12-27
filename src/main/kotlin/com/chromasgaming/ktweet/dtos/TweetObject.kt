package com.chromasgaming.ktweet.dtos

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import java.time.Instant


// reference: https://developer.twitter.com/en/docs/twitter-api/data-dictionary/object-model/tweet
@Serializable
data class TweetObject(
    val id: String,
    val text: String,
    val attachments: Attachments?,
    val author_id: String?,
    val context_annotations: List<String>?,
    val conversation_id: String?,
    @Contextual val created_at: Instant?,
    val entities: JsonObject?,
    val geo: Geo?,
    val in_reply_to_user_id: String?,
    val lang: String?,
    val non_public_metrics: NonPublicMetrics?,
    val organic_metrics: OrganicMetrics?,
    val possibly_sensitive: Boolean?,
    val promoted_metrics: PromotedMetrics?,
    val public_metrics: PublicMetrics?,
    val referenced_tweets: ArrayList<JsonObject>?,
    val reply_settings: String?,
    val source: String?,
    val withheld: WithHolding?

)

@Serializable
data class Attachments(val poll_ids: ArrayList<String>?, val media_keys: ArrayList<String>?)

@Serializable
data class Geo(val coordinates: Coordinates?, val place_id: String?)

@Serializable
data class Coordinates(val type: String?, val coordinates: ArrayList<Float>?)

@Serializable
data class NonPublicMetrics(val impression_count: Int?, val url_link_clicks: Int?, val user_profile_clicks: Int?)

@Serializable
data class OrganicMetrics(
    val impression_count: Int?,
    val like_count: Int?,
    val reply_count: Int?,
    val retweet_count: Int?,
    val url_link_clicks: Int?,
    val user_profile_clicks: Int?
)

@Serializable
data class PromotedMetrics(
    val impression_count: Int?,
    val like_count: Int?,
    val reply_count: Int?,
    val retweet_count: Int?,
    val url_link_clicks: Int?,
    val user_profile_clicks: Int?
)

@Serializable
data class PublicMetrics(val retweet_count: Int?, val reply_count: Int?, val like_count: Int?, val quote_count: Int?)

@Serializable
data class WithHolding(val copyright: Boolean?, val country_codes: ArrayList<String>?)