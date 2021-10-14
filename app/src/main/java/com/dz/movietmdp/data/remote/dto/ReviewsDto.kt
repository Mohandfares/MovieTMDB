package com.dz.movietmdp.data.remote.dto

import com.dz.movietmdp.domain.model.Reviews
import com.google.gson.annotations.SerializedName

data class ReviewsDto(
    val id: Int,
    val page: Int,
    val results: List<ReviewItemDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

fun ReviewsDto.toReviews(): Reviews =
    Reviews(
        results = results.map { it.toReviewItem() },
        totalPages = totalPages,
        totalResults = totalResults
    )