package com.relevanx.capstone_v1.api

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("caloryNeed")
	val caloryNeed: Int? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("proteinNeed")
	val proteinNeed: Int? = null,

	@field:SerializedName("fiberNeed")
	val fiberNeed: Int? = null,

	@field:SerializedName("fatNeed")
	val fatNeed: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("weight")
	val weight: String? = null,

	@field:SerializedName("carbohydrateNeed")
	val carbohydrateNeed: Int? = null,

	@field:SerializedName("birthDate")
	val birthDate: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("height")
	val height: String? = null
)
