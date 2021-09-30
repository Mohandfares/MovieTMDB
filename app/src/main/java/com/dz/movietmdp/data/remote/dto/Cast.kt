package com.dz.movietmdp.data.remote.dto

import com.dz.movietmdp.common.Constants
import com.dz.movietmdp.domain.model.Actor
import com.google.gson.annotations.SerializedName

data class Cast(
    val adult: Boolean,
    @SerializedName("cast_id")
    val castId: Int,
    val character: String,
    @SerializedName("credit_id")
    val creditId: String,
    val gender: Int?,
    val id: Int,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    @SerializedName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String?
)

fun Cast.toActor(): Actor =
    Actor(
        id = id,
        name = name,
        character = character,
        profilePath = if (profilePath != null) "${Constants.IMG_SOURCE_URL}$profilePath" else null
    )