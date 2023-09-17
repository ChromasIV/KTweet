package com.chromasgaming.ktweet.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OAuth2(
    @SerialName("token_type") val tokenType: String,
    @SerialName("expires_in") val expiresIn: Int? = null,
    @SerialName("access_token") val accessToken: String,
    val scope: String? = null,
    @SerialName("refresh_token") val refreshToken: String? = null
)
