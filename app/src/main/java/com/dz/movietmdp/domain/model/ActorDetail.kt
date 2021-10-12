package com.dz.movietmdp.domain.model

data class ActorDetail(
    val name: String,
    val biography: String,
    val posterPath: String?,
    val knownFor: List<MovieItem>,
    val socialLinks: SocialLinks
)
