package com.dz.movietmdp.domain.repository

import com.dz.movietmdp.data.remote.dto.MovieDetailDto
import com.dz.movietmdp.data.remote.dto.MoviesDto
import com.dz.movietmdp.domain.usecase.getmovies.TrendingFilter

interface MoviesRepository {

    suspend fun getPopularMovies(): MoviesDto
    suspend fun getTopRatedMovies(): MoviesDto
    suspend fun getTrendingMovies(trendingFilter: TrendingFilter): MoviesDto
    suspend fun getMovie(movieId: String): MovieDetailDto
}