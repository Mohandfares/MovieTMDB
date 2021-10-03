package com.dz.movietmdp.domain.model

data class MovieItem(
    val id: Int = 0,
    val originalTitle: String,
    val voteAverage: Double,
    val posterPath: String?,
    val releaseDate: String,
    val originalLanguage: String,
)
