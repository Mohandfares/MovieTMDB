package com.dz.movietmdp.data.remote

import com.dz.movietmdp.common.Constants
import com.dz.movietmdp.data.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieTmdbApi {

    @Headers("Authorization: Bearer ${Constants.ApiAccessToken}")
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): MoviesDto

    @Headers("Authorization: Bearer ${Constants.ApiAccessToken}")
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("page") page: Int): MoviesDto

    @Headers("Authorization: Bearer ${Constants.ApiAccessToken}")
    @GET("trending/movie/week")
    suspend fun getTrendingWeekMovies(@Query("page") page: Int): MoviesDto

    @Headers("Authorization: Bearer ${Constants.ApiAccessToken}")
    @GET("trending/movie/day")
    suspend fun getTrendingDayMovies(@Query("page") page: Int): MoviesDto

    @Headers("Authorization: Bearer ${Constants.ApiAccessToken}")
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("query") query: String
    ): MoviesDto

    @Headers("Authorization: Bearer ${Constants.ApiAccessToken}")
    @GET("movie/{movieId}")
    suspend fun getMovie(@Path("movieId") movieId: String): MovieDetailDto

    @Headers("Authorization: Bearer ${Constants.ApiAccessToken}")
    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(@Path("movieId") movieId: String): CreditsDto

    @Headers("Authorization: Bearer ${Constants.ApiAccessToken}")
    @GET("movie/{movieId}/reviews")
    suspend fun getMovieReviews(
        @Path("movieId") movieId: String,
        @Query("page") page: Int
    ): ReviewsDto

    @Headers("Authorization: Bearer ${Constants.ApiAccessToken}")
    @GET("movie/{movieId}/videos")
    suspend fun getMovieTrailer(@Path("movieId") movieId: String): TrailersDto

    @Headers("Authorization: Bearer ${Constants.ApiAccessToken}")
    @GET("credit/{creditId}")
    suspend fun getCredit(@Path("creditId") creditId: String): CreditDetailDto

    @Headers("Authorization: Bearer ${Constants.ApiAccessToken}")
    @GET("person/{personId}")
    suspend fun getPerson(@Path("personId") personId: String): PersonDetailDto

    @Headers("Authorization: Bearer ${Constants.ApiAccessToken}")
    @GET("person/{personId}/external_ids")
    suspend fun getSocialLinks(@Path("personId") personId: String): SocialLinksDto
}