package com.relevanx.capstone_v1.api

import com.google.gson.annotations.SerializedName

data class HomeResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("caloryPerc")
	val caloryPerc: Double?  = null,

	@field:SerializedName("proteinPerc")
	val proteinPerc: Double?  = null,

	@field:SerializedName("fatPerc")
	val fatPerc: Double?  = null,

	@field:SerializedName("fiberPerc")
	val fiberPerc: Double?  = null,

	@field:SerializedName("carbohydratePerc")
	val carbohydratePerc: Double?  = null,

	@field:SerializedName("currentNutrientsPerc")
	val currentNutrientsPerc: Double?  = null
)
