package com.relevanx.capstone_v1.activity.history.main_history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.relevanx.capstone_v1.api.ApiConfig
import com.relevanx.capstone_v1.api.HistoryResponse
import com.relevanx.capstone_v1.api.HistoryResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel : ViewModel() {
    private val _api = MutableLiveData<HistoryResponse>()
    val api: MutableLiveData<HistoryResponse> = _api

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getHistory(token: String, uid: String, page: Int, size: Int) {
        _isLoading.postValue(true)
        val client = ApiConfig.getApiService()
        client.getHistory(token, uid, page, size).enqueue(object : Callback<List<HistoryResponseItem>> {
            override fun onResponse(
                call: Call<List<HistoryResponseItem>>,
                response: Response<List<HistoryResponseItem>>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    val historyResponseItems = response.body()
                    _api.value = historyResponseItems?.let { HistoryResponse(it) }
                    Log.d("HistoryViewModel", "onResponse: ${response.code()}")
                } else {
                    Log.d("HistoryViewModel", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<HistoryResponseItem>>, t: Throwable) {
                _isLoading.postValue(false)
                Log.d("HistoryViewModel", "onFailure: ${t.message}")
                t.printStackTrace()
            }
        })
    }
}