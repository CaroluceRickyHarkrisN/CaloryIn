package com.relevanx.capstone_v1.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiUsd {
    @GET("foods/search")
    fun searchFoods(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Call<UsdaResponse>
}