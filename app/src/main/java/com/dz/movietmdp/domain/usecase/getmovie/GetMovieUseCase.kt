package com.dz.movietmdp.domain.usecase.getmovie

import com.dz.movietmdp.common.Resource
import com.dz.movietmdp.data.remote.dto.toActor
import com.dz.movietmdp.data.remote.dto.toMovieDetail
import com.dz.movietmdp.domain.model.MovieDetail
import com.dz.movietmdp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(movieId: String): Flow<Resource<MovieDetail>> = flow {
        try {
            emit(Resource.Loading<MovieDetail>())
            val actors = repository.getMovieCredits(movieId).cast.map { it.toActor() }
            val movie = repository.getMovie(movieId).toMovieDetail(actors)
            emit(Resource.Success<MovieDetail>(movie))
        } catch (e: HttpException) {
            emit(Resource.Error<MovieDetail>(e.localizedMessage ?: "An unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error<MovieDetail>("Make sure you are connected to the internet and try again"))
        }
    }
}