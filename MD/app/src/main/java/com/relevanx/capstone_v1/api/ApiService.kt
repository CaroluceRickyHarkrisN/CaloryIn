package com.relevanx.capstone_v1.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("foods/records/{uid}")
    @Headers("Content-Type: application/json")
    fun getHistory(
        @Header("Authorization") token: String,
        @Path("uid") uid: String,
        @Query("page") page: Int,
        @Query("limit") size: Int
    ): Call<List<HistoryResponseItem>>

    @GET("foods/records/{uid}/{recordId}")
    @Headers("Content-Type: application/json")
    fun getDetailHistory(
        @Header("Authorization") token: String,
        @Path("uid") uid: String,
        @Path("recordId") recordId: String,
    ): Call<DetailHistoryResponse>

    @GET("users/{uid}")
    @Headers("Content-Type: application/json")
    fun getProfile(
        @Header("Authorization") token: String,
        @Path("uid") uid: String,
    ): Call<ProfileResponse>

    @GET("foods/dashboard/{uid}")
    @Headers("Content-Type: application/json")
    fun getHome(
        @Header("Authorization") token: String,
        @Path("uid") uid: String,
    ): Call<HomeResponse>

    @POST("foods/records/{uid}")
    @Headers("Content-Type: application/json")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Path("uid") uid: String,
        @Body uploadBody: UploadBody
    ): Call<UploadResponse>

    @POST("users/register")
    @Headers("Content-Type: application/json")
    fun register(
        @Header("Authorization") token: String,
        @Body regisBody: RegisBody
    ): Call<ResponseBody>

    @PUT("users/update/{uid}")
    @Headers("Content-Type: application/json")
    fun updateUser(
        @Header("Authorization") token: String,
        @Path("uid") uid: String,
        @Body updateBody: UpdateUserResponse
    ): Call<ResponseBody>

    @DELETE("foods/records/{uid}/{recordId}")
    fun deleteHistory(
        @Header("Authorization") token: String,
        @Path("uid") uid: String,
        @Path("recordId") recordId: String,
    ): Call<ResponseBody>

}