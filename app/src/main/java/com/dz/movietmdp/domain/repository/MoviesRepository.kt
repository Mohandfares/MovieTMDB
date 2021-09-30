package com.dz.movietmdp.domain.repository

import com.dz.movietmdp.data.remote.dto.CreditsDto
import com.dz.movietmdp.data.remote.dto.MovieDetailDto
import com.dz.movietmdp.data.remote.dto.MoviesDto
import com.dz.movietmdp.domain.usecase.getmovies.TrendingFilter

interface MoviesRepository {

    suspend fun getPopularMovies(page: Int): MoviesDto
    suspend fun getTopRatedMovies(page: Int): MoviesDto
    suspend fun getTrendingMovies(trendingFilter: TrendingFilter,page: Int): MoviesDto
    suspend fun searchMovies(page: Int, query: String): MoviesDto
    suspend fun getMovie(movieId: String): MovieDetailDto
    suspend fun getMovieCredits(movieId: String): CreditsDto
}