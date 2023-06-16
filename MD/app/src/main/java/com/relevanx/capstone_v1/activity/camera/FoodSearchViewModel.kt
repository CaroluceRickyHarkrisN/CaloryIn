package com.relevanx.capstone_v1.activity.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.relevanx.capstone_v1.api.ApiConfig
import com.relevanx.capstone_v1.api.UsdaResponse

class FoodSearchViewModel : ViewModel() {
    private val _api = MutableLiveData<UsdaResponse>()
    val api: MutableLiveData<UsdaResponse> = _api

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val apiKey = "dqphFyxvbPhRQHNngfLVABVDbA4RDWDjAKRngbnR"
    fun searchFoods(query: String){
        _isLoading.postValue(true)
        val client = ApiConfig.getApiUsda()
        client.searchFoods("DEMO_KEY", query).enqueue(object : retrofit2.Callback<UsdaResponse> {
            override fun onResponse(
                call: retrofit2.Call<UsdaResponse>,
                response: retrofit2.Response<UsdaResponse>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _api.value = response.body()
                }
            }

            override fun onFailure(call: retrofit2.Call<UsdaResponse>, t: Throwable) {
                _isLoading.postValue(false)
                t.printStackTrace()
            }
        })
    }
}