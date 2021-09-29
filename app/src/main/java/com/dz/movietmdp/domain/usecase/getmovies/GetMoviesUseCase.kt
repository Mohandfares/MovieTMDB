package com.dz.movietmdp.domain.usecase.getmovies

import com.dz.movietmdp.common.Resource
import com.dz.movietmdp.data.remote.dto.toMovies
import com.dz.movietmdp.domain.model.Movies
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
        trendingFilter: TrendingFilter,
        page: Int
    ): Flow<Resource<Movies>> = flow {
        try {
            emit(Resource.Loading<Movies>())
            val movies = when (moviesFilter) {
                MoviesFilter.Popular -> repository.getPopularMovies(page)
                MoviesFilter.Rated -> repository.getTopRatedMovies(page)
                MoviesFilter.Trending -> repository.getTrendingMovies(trendingFilter,page)
            }
            emit(Resource.Success<Movies>(movies.toMovies()))
        } catch (e: HttpException) {
            emit(Resource.Error<Movies>(e.localizedMessage ?: "An unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error<Movies>("Make sure you are connected to the internet and try again"))
        }
    }
}

enum class MoviesFilter {
    Popular, Rated, Trending
}

enum class TrendingFilter {
    Week,Day
}