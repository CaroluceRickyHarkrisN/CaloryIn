package com.relevanx.capstone_v1.activity.camera

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.relevanx.capstone_v1.api.ApiConfig
import com.relevanx.capstone_v1.api.UploadBody
import com.relevanx.capstone_v1.api.UploadResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraViewModel : ViewModel() {

    private var _isUpload = MutableLiveData<Boolean>()
    val isUpload: MutableLiveData<Boolean> = _isUpload
    private val storageRef: StorageReference = FirebaseStorage.getInstance().reference

    fun uploadImage(uri: Uri?, fileName: String, token: String, uid: String, name: String, ) {
        val uploadTask = uri?.let { storageRef.child("file/$fileName").putFile(it) }
        uploadTask?.addOnSuccessListener { uploadTaskSnapshot ->
            uploadTaskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                val imageUrl = downloadUri.toString()
                Log.e("Firebase", "Image URL: $imageUrl")

                // Call your API to upload the image URL
                val client = ApiConfig.getApiService()
                client.uploadImage(token, uid, UploadBody(imageUrl, "12-12-2012", name))
                    .enqueue(object : Callback<UploadResponse> {
                        override fun onResponse(
                            call: Call<UploadResponse>,
                            response: Response<UploadResponse>
                        ) {
                            if (response.isSuccessful) {
                                _isUpload = MutableLiveData(true)
                                Log.e("CheckRes", "Berhasil : $imageUrl")
                                Log.d("HistoryViewModel", "onResponse: ${response.code()}")
                            } else {
                                _isUpload = MutableLiveData(false)
                                Log.d("HistoryViewModel", "onResponse: ${response.code()}")
                                Log.e("CheckRes", "Failed")
                            }
                        }

                        override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                            _isUpload = MutableLiveData(false)
                            Log.e("CheckRes", "Failed")
                        }
                    })
            }.addOnFailureListener {
                Log.e("Firebase", "Failed to get image URL")

            }
        }?.addOnFailureListener {
            Log.e("Firebase", "Image Upload failed")

        }
    }

}