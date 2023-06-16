package com.relevanx.capstone_v1.activity.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.relevanx.capstone_v1.api.ApiConfig
import com.relevanx.capstone_v1.api.FailRegisResponse
import com.relevanx.capstone_v1.api.HomeResponse
import com.relevanx.capstone_v1.api.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _api = MutableLiveData<HomeResponse>()
    val api: MutableLiveData<HomeResponse> = _api

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getHome(token: String, uid: String) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService()

        client.getHome(token, uid).enqueue(object : Callback<HomeResponse> {
            override fun onResponse(
                call: Call<HomeResponse>,
                response: Response<HomeResponse>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _api.postValue(response.body())
                    Log.d("HomeViewModel", "onResponse: ${response.code()}")
                } else {
                    Log.d("HomeViewModel", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.d("HomeViewModel", "onFailure: ${t.message}")
                t.printStackTrace()
            }
        })
    }

    private val _aapi = MutableLiveData<Boolean>()
    val aapi: LiveData<Boolean> = _aapi

    private val _iisLoading = MutableLiveData<Boolean>()
    val iisLoading: LiveData<Boolean> = _iisLoading

    fun getProfile(token: String, uid: String) {
        _iisLoading.postValue(true)
        val client = ApiConfig.getApiService()

        client.getProfile(token, uid).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _iisLoading.postValue(false)
                if (response.isSuccessful) {
                    _aapi.postValue(false)
                    Log.d("ProfileXXViewModel", "onResponse: ${response.code()}")
                } else {
                    _aapi.postValue(true)
                    Log.d("ProfileXXViewModel", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _iisLoading.postValue(false)
                Log.d("ProfileViewModel", "onFailure: ${t.message}")
                t.printStackTrace()
            }
        })
    }

}