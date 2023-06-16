package com.relevanx.capstone_v1.api

import com.google.gson.annotations.SerializedName

data class UploadBody(

	@field:SerializedName("imageURL")
	val imageURL: String? = null,

	@field:SerializedName("dateRecord")
	val dateRecord: String? = null,

	@field:SerializedName("food")
	val food: String? = null
)
