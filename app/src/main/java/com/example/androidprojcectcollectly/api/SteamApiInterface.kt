package com.example.androidprojcectcollectly.api


import com.example.androidprojcectcollectly.models.SteamProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
/**
 *The interface that will be used to get the steam profile data from the api
 * Api key and steam id is needed
 */

interface SteamApiInterface {
    @GET("ISteamUser/GetPlayerSummaries/v0002")
    fun getSteamProfile(
        @Query("key") type: String,
        @Query("steamids") steamids: String



    ): Call<SteamProfile>

}