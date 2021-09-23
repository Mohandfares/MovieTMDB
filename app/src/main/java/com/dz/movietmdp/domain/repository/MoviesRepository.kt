package com.dz.movietmdp.domain.repository

import com.dz.movietmdp.data.remote.dto.MovieDetailDto
import com.dz.movietmdp.data.remote.dto.MoviesDto

interface MoviesRepository {

    suspend fun getPopularMovies(): MoviesDto
    suspend fun getTopRatedMovies(): MoviesDto
    suspend fun getTrendingMovies(): MoviesDto
    suspend fun getMovie(movieId: String): MovieDetailDto
}