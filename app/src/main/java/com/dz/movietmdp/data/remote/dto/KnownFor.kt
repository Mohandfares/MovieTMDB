package com.dz.movietmdp.data.remote.dto

import com.dz.movietmdp.common.Constants
import com.dz.movietmdp.domain.model.MovieItem
import com.google.gson.annotations.SerializedName

data class KnownFor(
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Double>,
    val id: Double,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String?,
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    val overview: String?,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Double
)

fun KnownFor.toMovie(): MovieItem =
    MovieItem(
        originalTitle = title ?: originalName ?: "",
        originalLanguage = originalLanguage,
        posterPath = if (posterPath != null) "${Constants.IMG_SOURCE_URL}$posterPath" else null,
        voteAverage = voteAverage,
        releaseDate = releaseDate ?: ""
    )