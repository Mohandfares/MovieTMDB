package com.dz.movietmdp.data.remote.dto

import com.dz.movietmdp.common.Constants
import com.dz.movietmdp.domain.model.ReviewItem
import com.google.gson.annotations.SerializedName

data class ReviewItemDto(
    val author: String,
    @SerializedName("author_details")
    val authorDetails: AuthorDetails,
    val content: String,
    @SerializedName("created_at")
    val createdAt: String,
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    val url: String
)

fun ReviewItemDto.toReviewItem(): ReviewItem =
    ReviewItem(
        id = id,
        author = author,
        content = content,
        createdAt = createdAt,
        avatarPath = if (authorDetails.avatarPath != null) {
            if (authorDetails.avatarPath.startsWith("/https")) {
                authorDetails.avatarPath.substringAfter("/")
            } else {
                "${Constants.IMG_SOURCE_URL}${authorDetails.avatarPath}"
            }
        } else {
            null
        },
        rating = authorDetails.rating
    )