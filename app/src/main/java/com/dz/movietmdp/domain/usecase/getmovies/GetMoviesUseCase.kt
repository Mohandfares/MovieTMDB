package com.dz.movietmdp.domain.usecase.getmovies

import com.dz.movietmdp.common.Resource
import com.dz.movietmdp.data.remote.dto.toMovieItem
import com.dz.movietmdp.domain.model.MovieItem
import com.dz.movietmdp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(
        moviesFilter: MoviesFilter,
        trendingFilter: TrendingFilter
    ): Flow<Resource<List<MovieItem>>> = flow {
        try {
            emit(Resource.Loading<List<MovieItem>>())
            val movies = when (moviesFilter) {
                MoviesFilter.Popular -> repository.getPopularMovies()
                MoviesFilter.Rated -> repository.getTopRatedMovies()
                MoviesFilter.Trending -> repository.getTrendingMovies(trendingFilter)
            }
            emit(Resource.Success<List<MovieItem>>(movies.results.map { it.toMovieItem() }))
        } catch (e: HttpException) {
            emit(Resource.Error<List<MovieItem>>(e.localizedMessage ?: "An unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error<List<MovieItem>>("Make sure you are connected to the internet and try again"))
        }
    }
}

enum class MoviesFilter {
    Popular, Rated, Trending
}

enum class TrendingFilter {
    Week,Day
}