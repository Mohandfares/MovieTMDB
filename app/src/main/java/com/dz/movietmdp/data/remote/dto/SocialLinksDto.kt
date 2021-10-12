package com.dz.movietmdp.data.remote.dto

import com.dz.movietmdp.domain.model.SocialLinks
import com.google.gson.annotations.SerializedName

data class SocialLinksDto(
    @SerializedName("facebook_id")
    val facebookId: String?,
    @SerializedName("freebase_id")
    val freebaseId: String?,
    @SerializedName("freebase_mid")
    val freebaseMid: String?,
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("instagram_id")
    val instagramId: String?,
    @SerializedName("tvrage_id")
    val tvrageId: Int?,
    @SerializedName("twitter_id")
    val twitterId: String?
)

fun SocialLinksDto.toSocialLinks(): SocialLinks =
    SocialLinks(
        facebook = facebookId,
        instagram = instagramId,
        twitter = twitterId
    )