package com.relevanx.capstone_v1.api

import com.google.gson.annotations.SerializedName

data class DetailHistoryResponse(

	@field:SerializedName("recordId")
	val recordId: String? = null,

	@field:SerializedName("calory")
	val calory: Double? = null,

	@field:SerializedName("nameFood")
	val nameFood: String? = null,

	@field:SerializedName("protein")
	val protein: Any? = null,

	@field:SerializedName("imageURL")
	val imageURL: String? = null,

	@field:SerializedName("dateRecord")
	val dateRecord: String? = null,

	@field:SerializedName("lipid")
	val lipid: Any? = null,

	@field:SerializedName("carbohydrate")
	val carbohydrate: Any? = null
)
