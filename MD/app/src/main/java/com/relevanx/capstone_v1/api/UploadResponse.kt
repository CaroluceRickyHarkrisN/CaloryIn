package com.relevanx.capstone_v1.api

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("Calory")
	val calory: Double?  = null,

	@field:SerializedName("nameFood")
	val nameFood: String? = null,

	@field:SerializedName("protein")
	val protein: Double? = null,

	@field:SerializedName("imageURL")
	val imageURL: String? = null,

	@field:SerializedName("dateRecord")
	val dateRecord: String? = null,

	@field:SerializedName("Carbohydrate")
	val carbohydrate: Double? = null,

	@field:SerializedName("lipid")
	val lipid: Double? = null
)
