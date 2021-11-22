package com.chromasgaming.ktweet.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostTweetDTO(val data: PostTweetMetaDTO) {
    @Serializable
    data class PostTweetMetaDTO(val id: String, val text: String)
}

@Serializable
data class Tweet(val text: String, val media: Media? = null) {
    @Serializable
    data class Media(val media_ids: List<String>? = null, val tagged_user_ids: List<String>? = null)
}