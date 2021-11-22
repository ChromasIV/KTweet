package com.chromasgaming.ktweet.dto

import kotlinx.serialization.Serializable

@Serializable
data class RequestTokenDTO(val requestToken: String, val requestTokenSecret: String, val callBackUrl: String)
