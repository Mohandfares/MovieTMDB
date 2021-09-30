package com.dz.movietmdp.domain.model

import com.dz.movietmdp.data.remote.dto.*

data class MovieDetail(
    val backdropPath: String?,
    val belongsToCollection: BelongsToCollection?,
    val budget: Int,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>?,
    val productionCountries: List<ProductionCountry>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int?,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String,
    val tagline: String?,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val actors: List<Actor>
)
