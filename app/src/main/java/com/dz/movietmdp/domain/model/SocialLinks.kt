package com.dz.movietmdp.domain.model

data class SocialLinks(
    val facebook: String?,
    val instagram: String?,
    val twitter: String?,
    val empty: Boolean = facebook == null && instagram == null && twitter == null
)
