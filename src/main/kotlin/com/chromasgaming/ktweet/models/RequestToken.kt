package com.chromasgaming.ktweet.models

import kotlinx.serialization.Serializable

@Serializable
data class RequestToken(val requestToken: String, val requestTokenSecret: String, val callBackUrl: String)
