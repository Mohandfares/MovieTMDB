package com.dz.movietmdp.domain.usecase.getactor

import com.dz.movietmdp.common.Constants
import com.dz.movietmdp.common.Resource
import com.dz.movietmdp.data.remote.dto.toMovie
import com.dz.movietmdp.data.remote.dto.toSocialLinks
import com.dz.movietmdp.domain.model.ActorDetail
import com.dz.movietmdp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetActorUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    operator fun invoke(personId: String,creditId: String): Flow<Resource<ActorDetail>> = flow {
        try {
            emit(Resource.Loading<ActorDetail>())
            val movies = repository.getCredit(creditId).person.knownFor.map { it.toMovie() }
            val person = repository.getPerson(personId)
            val socialLinks = repository.getSocialLinks(personId).toSocialLinks()
            val actorDetail = ActorDetail(
                name = person.name,
                biography = person.biography,
                posterPath = if (person.profilePath != null) "${Constants.IMG_SOURCE_URL}${person.profilePath}" else null,
                knownFor = movies,
                socialLinks = socialLinks
            )
            emit(Resource.Success<ActorDetail>(actorDetail))
        } catch (e: HttpException) {
            emit(Resource.Error<ActorDetail>(e.localizedMessage ?: "An unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error<ActorDetail>("Make sure you are connected to the internet and try again"))
        }
    }
}