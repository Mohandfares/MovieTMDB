package com.dz.movietmdp.domain.model

data class ReviewItem(
    val id: String,
    val author: String,
    val content: String,
    val createdAt: String,
    val avatarPath: String?,
    val rating: Double?
)
