package com.dz.movietmdp.domain.usecase.getmovie

import com.dz.movietmdp.common.Resource
import com.dz.movietmdp.data.remote.dto.toActor
import com.dz.movietmdp.data.remote.dto.toMovieDetail
import com.dz.movietmdp.domain.model.Actor
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
            val movieCredits = repository.getMovieCredits(movieId)
            val actors = try {
                movieCredits.cast.map { it.toActor() }
            } catch (e: Exception) {
                emptyList()
            }
            val director = try {
                movieCredits.crew.first { it.department == "Directing" }.toActor()
            } catch (e: Exception) {
                null
            }
            val videos = repository.getMovieTrailer(movieId)
            val trailer = try {
                val trailerKey = videos.results.first { it.official && it.site == "YouTube" }.key
                "https://www.youtube.com/watch?v=$trailerKey"
            } catch (e: Exception) {
                ""
            }
            val movie = repository.getMovie(movieId).toMovieDetail(actors,director,trailer)
            emit(Resource.Success<MovieDetail>(movie))
        } catch (e: HttpException) {
            emit(Resource.Error<MovieDetail>(e.localizedMessage ?: "An unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error<MovieDetail>("Make sure you are connected to the internet and try again"))
        }
    }
}