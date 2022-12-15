package com.example.androidprojcectcollectly.api


import com.example.androidprojcectcollectly.models.SteamProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SteamApiInterface {
    @GET("ISteamUser/GetPlayerSummaries/v0002")
    fun getSteamProfile(
        @Query("key") type: String,
        @Query("steamids") steamids: String



    ): Call<SteamProfile>

}