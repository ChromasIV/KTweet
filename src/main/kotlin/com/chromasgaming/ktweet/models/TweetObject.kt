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
    @SerialName("author_id") val authorId: String? = null,
    @SerialName("context_annotations") val contextAnnotations: List<JsonObject>? = null,
    @SerialName("conversation_id") val conversationId: String? = null,
    @SerialName("created_at") @Contextual val createdAt: String? = null,
    val entities: JsonObject? = null,
    val geo: Geo? = null,
    @SerialName("in_reply_to_user_id") val inReplyToUserId: String? = null,
    val lang: String? = null,
    @SerialName("non_public_metrics") val nonPublicMetrics: NonPublicMetrics? = null,
    @SerialName("organic_metrics") val organicMetrics: OrganicMetrics? = null,
    @SerialName("possibly_sensitive") val possiblySensitive: Boolean? = null,
    @SerialName("promoted_metrics") val promotedMetrics: PromotedMetrics? = null,
    @SerialName("public_metrics") val publicMetrics: PublicMetrics? = null,
    @SerialName("referenced_tweets") val referencedTweets: ArrayList<JsonObject>? = null,
    @SerialName("reply_settings") val replySettings: String? = null,
    val source: String? = null,
    val withheld: WithHolding? = null

)

@Serializable
data class Attachments(
    @SerialName("poll_ids") val pollIds: ArrayList<String>? = null,
    @SerialName("media_keys") val mediaKeys: ArrayList<String>? = null
)

@Serializable
data class Geo(val coordinates: Coordinates? = null, @SerialName("place_id") val placeId: String? = null)

@Serializable
data class Coordinates(val type: String? = null, val coordinates: ArrayList<Float>? = null)

@Serializable
data class NonPublicMetrics(
    @SerialName("impression_count") val impressionCount: Int? = null,
    @SerialName("url_link_clicks") val urlLinkClicks: Int? = null,
    @SerialName("user_profile_clicks") val userProfileClicks: Int? = null
)

@Serializable
data class OrganicMetrics(
    @SerialName("impression_count") val impressionCount: Int? = null,
    @SerialName("like_count") val likeCount: Int? = null,
    @SerialName("reply_count") val replyCount: Int? = null,
    @SerialName("retweet_count") val retweetCount: Int? = null,
    @SerialName("url_link_clicks") val urlLinkClicks: Int? = null,
    @SerialName("user_profile_clicks") val userProfileClicks: Int? = null
)

@Serializable
data class PromotedMetrics(
    @SerialName("impression_count")  val impressionCount: Int? = null,
    @SerialName("like_count")  val likeCount: Int? = null,
    @SerialName("reply_count")  val replyCount: Int? = null,
    @SerialName("retweet_count")   val retweetCount: Int? = null,
    @SerialName("url_link_clicks")   val urlLinkClicks: Int? = null,
    @SerialName("user_profile_clicks")   val userProfileClicks: Int? = null
)

@Serializable
data class PublicMetrics(
    @SerialName("retweet_count")  val retweetCount: Int? = null,
    @SerialName("reply_count")   val replyCount: Int? = null,
    @SerialName("like_count")   val likeCount: Int? = null,
    @SerialName("quote_count")   val quoteCount: Int? = null
)

@Serializable
data class WithHolding(val copyright: Boolean? = null, @SerialName("country_codes") val countryCodes: ArrayList<String>? = null)
