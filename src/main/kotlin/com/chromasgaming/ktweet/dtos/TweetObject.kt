package com.chromasgaming.ktweet.dtos

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.Instant


// reference: https://developer.twitter.com/en/docs/twitter-api/data-dictionary/object-model/tweet
@Serializable
data class TweetObject(
    val id: String,
    val text: String,
    val attachments: Attachments? = null,
    val author_id: String? = null,
    val context_annotations: List<String>? = null,
    val conversation_id: String? = null,
    @Contextual val created_at: Instant? = null
)

@Serializable
data class Attachments(val poll_ids: ArrayList<String>? = null, val media_keys: ArrayList<String>? = null)