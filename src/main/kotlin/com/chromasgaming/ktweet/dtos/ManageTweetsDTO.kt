package com.chromasgaming.ktweet.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ManageTweetsDTO(val data: Response) {
    @Serializable
    data class Response(val id: String? = null, val text: String? = null, val deleted: Boolean? = null)
}

@Serializable
data class TweetDTO(val text: String, val media: Media? = null) {
    @Serializable
    data class Media(val media_ids: List<String>? = null, val tagged_user_ids: List<String>? = null)
}
