package com.dz.movietmdp.data.remote.dto

import com.dz.movietmdp.common.Constants
import com.dz.movietmdp.domain.model.Actor
import com.google.gson.annotations.SerializedName

data class Crew(
    val adult: Boolean,
    @SerializedName("credit_id")
    val creditId: String,
    val department: String,
    val gender: Int?,
    val id: Int,
    val job: String,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    val name: String,
    @SerializedName("original_name")
    val originalName: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String?
)

fun Crew.toActor(): Actor =
    Actor(
        id = id,
        creditId = creditId,
        name = name,
        character = "Director",
        profilePath = if (profilePath != null) "${Constants.IMG_SOURCE_URL}$profilePath" else null
    )