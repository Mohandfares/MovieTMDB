package com.dz.movietmdp.domain.repository

import com.dz.movietmdp.data.remote.MoviedbApi
import com.dz.movietmdp.data.remote.dto.MovieDetailDto
import com.dz.movietmdp.data.remote.dto.MoviesDto
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: MoviedbApi
) : MoviesRepository {

    override suspend fun getPopularMovies(): MoviesDto = api.getPopularMovies()
    override suspend fun getTopRatedMovies(): MoviesDto = api.getTopRatedMovies()
    override suspend fun getTrendingMovies(): MoviesDto = api.getTrendingMovies()
    override suspend fun getMovie(movieId: String): MovieDetailDto = api.getMovie(movieId)

}