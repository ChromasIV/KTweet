package com.chromasgaming.ktweet.dtos

import kotlinx.serialization.Serializable

@Serializable
data class RequestTokenDTO(val requestToken: String, val requestTokenSecret: String, val callBackUrl: String)
