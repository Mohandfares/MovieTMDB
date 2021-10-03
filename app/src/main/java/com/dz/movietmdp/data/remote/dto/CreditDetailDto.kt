package com.dz.movietmdp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreditDetailDto(
    @SerializedName("credit_type")
    val creditType: String,
    val department: String,
    val id: String,
    val job: String,
    val media: Media,
    @SerializedName("media_type")
    val mediaType: String,
    val person: Person
)