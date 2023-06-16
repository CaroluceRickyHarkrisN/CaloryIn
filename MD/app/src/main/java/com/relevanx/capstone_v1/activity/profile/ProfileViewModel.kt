package com.relevanx.capstone_v1.activity.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.relevanx.capstone_v1.api.ApiConfig
import com.relevanx.capstone_v1.api.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    private val _api = MutableLiveData<ProfileResponse>()
    val api: MutableLiveData<ProfileResponse> = _api

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getProfile(token: String, uid: String) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService()

        client.getProfile(token, uid).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _api.postValue(response.body())
                    Log.d("ProfileViewModel", "onResponse: ${response.code()}")
                } else {
                    Log.d("ProfileViewModel", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.d("ProfileViewModel", "onFailure: ${t.message}")
                t.printStackTrace()
            }
        })
    }
}