package com.relevanx.capstone_v1.api

import com.google.gson.annotations.SerializedName

data class UsdaResponse(

	@field:SerializedName("foodSearchCriteria")
	val foodSearchCriteria: FoodSearchCriteria? = null,

	@field:SerializedName("foods")
	val foods: List<FoodsItem?>? = null,

	@field:SerializedName("totalHits")
	val totalHits: Int? = null,

	@field:SerializedName("totalPages")
	val totalPages: Int? = null,

	@field:SerializedName("pageList")
	val pageList: List<Int?>? = null,

	@field:SerializedName("currentPage")
	val currentPage: Int? = null,

	@field:SerializedName("aggregations")
	val aggregations: Aggregations? = null
)

data class FoodNutrientsItem(

	@field:SerializedName("unitName")
	val unitName: String? = null,

	@field:SerializedName("nutrientNumber")
	val nutrientNumber: String? = null,

	@field:SerializedName("foodNutrientSourceDescription")
	val foodNutrientSourceDescription: String? = null,

	@field:SerializedName("derivationId")
	val derivationId: Int? = null,

	@field:SerializedName("derivationDescription")
	val derivationDescription: String? = null,

	@field:SerializedName("percentDailyValue")
	val percentDailyValue: Int? = null,

	@field:SerializedName("foodNutrientSourceCode")
	val foodNutrientSourceCode: String? = null,

	@field:SerializedName("derivationCode")
	val derivationCode: String? = null,

	@field:SerializedName("foodNutrientId")
	val foodNutrientId: Int? = null,

	@field:SerializedName("nutrientId")
	val nutrientId: Int? = null,

	@field:SerializedName("indentLevel")
	val indentLevel: Int? = null,

	@field:SerializedName("foodNutrientSourceId")
	val foodNutrientSourceId: Int? = null,

	@field:SerializedName("rank")
	val rank: Int? = null,

	@field:SerializedName("nutrientName")
	val nutrientName: String? = null,

	@field:SerializedName("value")
	val value: Any? = null
)

data class FoodsItem(

	@field:SerializedName("marketCountry")
	val marketCountry: String? = null,

	@field:SerializedName("tradeChannels")
	val tradeChannels: List<String?>? = null,

	@field:SerializedName("foodNutrients")
	val foodNutrients: List<FoodNutrientsItem?>? = null,

	@field:SerializedName("packageWeight")
	val packageWeight: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("allHighlightFields")
	val allHighlightFields: String? = null,

	@field:SerializedName("householdServingFullText")
	val householdServingFullText: String? = null,

	@field:SerializedName("finalFoodInputFoods")
	val finalFoodInputFoods: List<Any?>? = null,

	@field:SerializedName("score")
	val score: Any? = null,

	@field:SerializedName("servingSizeUnit")
	val servingSizeUnit: String? = null,

	@field:SerializedName("ingredients")
	val ingredients: String? = null,

	@field:SerializedName("foodVersionIds")
	val foodVersionIds: List<Any?>? = null,

	@field:SerializedName("foodCategory")
	val foodCategory: String? = null,

	@field:SerializedName("servingSize")
	val servingSize: Any? = null,

	@field:SerializedName("foodAttributes")
	val foodAttributes: List<Any?>? = null,

	@field:SerializedName("brandName")
	val brandName: String? = null,

	@field:SerializedName("dataType")
	val dataType: String? = null,

	@field:SerializedName("brandOwner")
	val brandOwner: String? = null,

	@field:SerializedName("shortDescription")
	val shortDescription: String? = null,

	@field:SerializedName("fdcId")
	val fdcId: Int? = null,

	@field:SerializedName("microbes")
	val microbes: List<Any?>? = null,

	@field:SerializedName("foodMeasures")
	val foodMeasures: List<Any?>? = null,

	@field:SerializedName("gtinUpc")
	val gtinUpc: String? = null,

	@field:SerializedName("modifiedDate")
	val modifiedDate: String? = null,

	@field:SerializedName("foodAttributeTypes")
	val foodAttributeTypes: List<Any?>? = null,

	@field:SerializedName("publishedDate")
	val publishedDate: String? = null,

	@field:SerializedName("dataSource")
	val dataSource: String? = null
)

data class FoodSearchCriteria(

	@field:SerializedName("pageNumber")
	val pageNumber: Int? = null,

	@field:SerializedName("generalSearchInput")
	val generalSearchInput: String? = null,

	@field:SerializedName("query")
	val query: String? = null,

	@field:SerializedName("numberOfResultsPerPage")
	val numberOfResultsPerPage: Int? = null,

	@field:SerializedName("pageSize")
	val pageSize: Int? = null,

	@field:SerializedName("requireAllWords")
	val requireAllWords: Boolean? = null
)

data class FoodAttributeTypesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("foodAttributes")
	val foodAttributes: List<FoodAttributesItem?>? = null
)

data class Nutrients(
	val any: Any? = null
)

data class Aggregations(

	@field:SerializedName("dataType")
	val dataType: DataType? = null,

	@field:SerializedName("nutrients")
	val nutrients: Nutrients? = null
)

data class FoodAttributesItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("value")
	val value: String? = null
)

data class DataType(

	@field:SerializedName("Branded")
	val branded: Int? = null
)
