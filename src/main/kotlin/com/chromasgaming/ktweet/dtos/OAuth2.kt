package com.chromasgaming.ktweet.dtos

import kotlinx.serialization.Serializable

@Serializable
data class BearerToken(
    val token_type: String,
    val expires_in: Int? = null,
    val access_token: String,
    val scope: String? = null,
    val refresh_token: String? = null
)