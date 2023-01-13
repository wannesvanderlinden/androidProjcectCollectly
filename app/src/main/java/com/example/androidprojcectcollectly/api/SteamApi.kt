package com.example.androidprojcectcollectly.api

import com.example.androidprojcectcollectly.models.SteamProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SteamApi {
    private var steamApi: SteamApiInterface? = null
   //Call the api with retro fit with the steamId of the searcher
    fun getData(steamId: String, callback: (obj: SteamProfile?) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getSteamApi().getSteamProfile("79342C8E8D35B857CE5C6000FA76F8A0", steamId)
            //println(call.request().url().url().toString())
            val response = call.execute()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                    error(response.message())
                }
            }
        }

    }
    private fun getSteamApi(): SteamApiInterface {
        if (steamApi == null) {
            steamApi = Retrofit.Builder().baseUrl("https://api.steampowered.com/")
                .addConverterFactory(MoshiConverterFactory.create()).build()
                .create(SteamApiInterface::class.java)
        }
        return steamApi!!
    }


}