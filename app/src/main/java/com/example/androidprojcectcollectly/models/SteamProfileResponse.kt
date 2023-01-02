package com.example.androidprojcectcollectly.models
/**
 *The response that I will get from the steam api
 */
data class SteamProfileResponse(
    val players : List<SteamProfilePlayer>
)
