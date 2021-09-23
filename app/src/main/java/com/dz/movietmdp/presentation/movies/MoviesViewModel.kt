package com.dz.movietmdp.presentation.movies

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.movietmdp.common.Resource
import com.dz.movietmdp.domain.usecase.getmovies.GetMoviesUseCase
import com.dz.movietmdp.domain.usecase.getmovies.MoviesFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: GetMoviesUseCase
) : ViewModel() {



    private val _state = mutableStateOf<MoviesListState>(MoviesListState())
    val state: State<MoviesListState> = _state

    private val _filterState = mutableStateOf<MoviesFilter>(MoviesFilter.Popular)
    val filterState: State<MoviesFilter> = _filterState

    init {
        getMovies()
    }

    fun filterChanged(filter: MoviesFilter) {
        _filterState.value = filter
        getMovies(filter)
    }

    fun getMovies(filter: MoviesFilter = MoviesFilter.Popular) {
        moviesUseCase(filter).onEach { result ->
            when (result) {
                is Resource.Error -> _state.value = MoviesListState(error = result.message ?: "An unexpected error")
                is Resource.Loading -> _state.value = MoviesListState(isLoading = true)
                is Resource.Success -> _state.value = MoviesListState(movies = result.data ?: emptyList())
            }
        }.launchIn(viewModelScope)
    }
}