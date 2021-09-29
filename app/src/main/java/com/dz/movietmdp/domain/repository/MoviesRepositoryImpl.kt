package com.dz.movietmdp.domain.repository

import com.dz.movietmdp.data.remote.MovieTmdbApi
import com.dz.movietmdp.data.remote.dto.MovieDetailDto
import com.dz.movietmdp.data.remote.dto.MoviesDto
import com.dz.movietmdp.domain.usecase.getmovies.TrendingFilter
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: MovieTmdbApi
) : MoviesRepository {

    override suspend fun getPopularMovies(page: Int): MoviesDto = api.getPopularMovies(page)
    override suspend fun getTopRatedMovies(page: Int): MoviesDto = api.getTopRatedMovies(page)
    override suspend fun getTrendingMovies(trendingFilter: TrendingFilter,page: Int): MoviesDto {
        return when (trendingFilter) {
            TrendingFilter.Week -> api.getTrendingWeekMovies(page)
            TrendingFilter.Day -> api.getTrendingDayMovies(page)
        }
    }
    override suspend fun searchMovies(page: Int, query: String): MoviesDto = api.searchMovies(page, query)
    override suspend fun getMovie(movieId: String): MovieDetailDto = api.getMovie(movieId)
}