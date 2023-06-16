package com.relevanx.capstone_v1.activity.history.detail_history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.relevanx.capstone_v1.api.ApiConfig
import com.relevanx.capstone_v1.api.DetailHistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailHistoryViewModel  : ViewModel() {
    private val _api = MutableLiveData<DetailHistoryResponse>()
    val api: MutableLiveData<DetailHistoryResponse> = _api

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailHistory(token: String, uid: String, recordId: String) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService()

        client.getDetailHistory(token, uid, recordId).enqueue(object : Callback<DetailHistoryResponse> {
            override fun onResponse(
                call: Call<DetailHistoryResponse>,
                response: Response<DetailHistoryResponse>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _api.postValue(response.body())
                    Log.d("HistoryViewModel", "onResponse: ${response.code()}")
                } else {
                    Log.d("HistoryViewModel", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<DetailHistoryResponse>, t: Throwable) {
                _isLoading.postValue(false)
                Log.d("HistoryViewModel", "onFailure: ${t.message}")
                t.printStackTrace()
            }
        })
    }
}