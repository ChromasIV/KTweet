package com.chromasgaming.ktweet.models

import kotlinx.serialization.Serializable

@Serializable
data class OAuth(val authToken: String, val authSecret: String, val userId: String, val userName: String)
