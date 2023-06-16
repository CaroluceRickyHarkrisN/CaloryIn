package com.relevanx.capstone_v1.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://capstone-project-389214.et.r.appspot.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }

        fun getApiUsda(): ApiUsd {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.nal.usda.gov/fdc/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiUsd::class.java)
        }
    }
}