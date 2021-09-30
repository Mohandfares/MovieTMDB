package com.dz.movietmdp.data.remote.dto

data class CreditsDto(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)