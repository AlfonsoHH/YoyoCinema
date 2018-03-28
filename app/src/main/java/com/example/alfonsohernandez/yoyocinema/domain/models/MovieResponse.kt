package com.example.alfonsohernandez.yoyocinema.domain.models

import com.google.gson.annotations.SerializedName

data class MovieResponse(
		@field:SerializedName("page")
	val page: Int? = null,
		@field:SerializedName("total_pages")
	val totalPages: Int? = null,
		@field:SerializedName("results")
	val results: List<MovieResultsItem>? = null,
		@field:SerializedName("total_results")
	val totalResults: Int? = null
)