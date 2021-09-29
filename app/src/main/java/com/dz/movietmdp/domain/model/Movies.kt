package com.dz.movietmdp.domain.model

data class Movies(
    val results: List<MovieItem>,
    val totalPages: Int,
    val totalResults: Int,
)