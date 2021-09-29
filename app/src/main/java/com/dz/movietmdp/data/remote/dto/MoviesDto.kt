package com.dz.movietmdp.data.remote.dto

import com.dz.movietmdp.domain.model.Movies
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

fun MoviesDto.toMovies(): Movies =
    Movies(
        results = results.map { it.toMovieItem() },
        totalPages = totalPages,
        totalResults = totalResults
    )