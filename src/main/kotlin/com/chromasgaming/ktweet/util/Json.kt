package com.chromasgaming.ktweet.util

import kotlinx.serialization.json.Json

internal val defaultJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = false
}
