package com.dz.movietmdp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MoviesDto(
    val page: Int,
    val results: List<MovieItemDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
    val dates: Dates?
)