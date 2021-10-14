package com.dz.movietmdp.domain.model

data class Reviews(
    val results: List<ReviewItem>,
    val totalPages: Int,
    val totalResults: Int,
)
