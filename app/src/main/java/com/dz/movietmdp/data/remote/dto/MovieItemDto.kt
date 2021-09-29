package com.dz.movietmdp.data.remote.dto

import com.dz.movietmdp.common.Constants
import com.dz.movietmdp.common.toDateFormat
import com.dz.movietmdp.domain.model.MovieItem
import com.google.gson.annotations.SerializedName

data class MovieItemDto(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("media_type")
    val mediaType: String?
)

fun MovieItemDto.toMovieItem(): MovieItem =
    MovieItem(
        id = id,
        originalTitle = originalTitle,
        posterPath = "${Constants.IMG_SOURCE_URL}$posterPath",
        voteAverage = voteAverage,
        releaseDate = releaseDate.toDateFormat(),
        originalLanguage = originalLanguage
    )