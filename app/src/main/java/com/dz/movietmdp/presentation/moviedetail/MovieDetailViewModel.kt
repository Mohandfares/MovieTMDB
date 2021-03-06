package com.dz.movietmdp.presentation.moviedetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dz.movietmdp.common.Constants.PARAM_MOVIE_ID
import com.dz.movietmdp.common.Resource
import com.dz.movietmdp.domain.model.ReviewItem
import com.dz.movietmdp.domain.pagingsource.ReviewsPagingSource
import com.dz.movietmdp.domain.repository.MoviesRepository
import com.dz.movietmdp.domain.repository.MoviesRepositoryImpl
import com.dz.movietmdp.domain.usecase.getmovie.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieUseCase: GetMovieUseCase,
    private val pagingSource: ReviewsPagingSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<MovieState>(MovieState())
    val state: State<MovieState> = _state

    init {
        savedStateHandle.get<String?>(PARAM_MOVIE_ID)?.let { movieId ->
            getMovie(movieId)
            ReviewsPagingSource.movieId = movieId
        }
    }

    private fun getMovie(movieId: String) {
        movieUseCase(movieId).onEach { result ->
            _state.value = when (result) {
                is Resource.Error -> MovieState(error = result.message ?: "An unexpected error")
                is Resource.Loading -> MovieState(isLoading = true)
                is Resource.Success -> MovieState(movieDetail = result.data)
            }
        }.launchIn(viewModelScope)
    }

    fun tryAgain() {
        savedStateHandle.get<String?>(PARAM_MOVIE_ID)?.let { movieId ->
            getMovie(movieId)
        }
    }

    val reviews: Flow<PagingData<ReviewItem>> =
        Pager(PagingConfig(pageSize = 20)) { pagingSource }.flow
}