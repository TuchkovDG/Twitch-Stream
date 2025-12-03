package com.example.twitchstreamer.data.remote.api

import com.example.twitchstreamer.data.remote.model.ViewersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ViewersApi {

    @GET("api/")
    suspend fun getViewers(@Query("results") amount: Int): ViewersResponse
}