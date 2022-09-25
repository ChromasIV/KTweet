package com.chromasgaming.ktweet.dtos

import kotlinx.serialization.Serializable

@Serializable
data class AccessToken(val authToken: String, val authSecret: String, val userId: String, val userName: String)