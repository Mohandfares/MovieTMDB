package com.dz.movietmdp.presentation.movies

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dz.movietmdp.common.Resource
import com.dz.movietmdp.domain.usecase.getmovies.GetMoviesUseCase
import com.dz.movietmdp.domain.usecase.getmovies.MoviesFilter
import com.dz.movietmdp.domain.usecase.getmovies.TrendingFilter
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

    private val _trendingState = mutableStateOf<TrendingFilter>(TrendingFilter.Week)
    val trendingState: State<TrendingFilter> = _trendingState

    init {
        getMovies()
    }

    fun filterChanged(filter: MoviesFilter) {
        _filterState.value = filter
        getMovies()
    }

    fun trendingChange(trending: TrendingFilter) {
        _trendingState.value = trending
        getMovies()
    }

    fun getMovies(
        filter: MoviesFilter = filterState.value,
        trending: TrendingFilter = trendingState.value
    ) {
        moviesUseCase(filter,trending).onEach { result ->
            _state.value = when (result) {
                is Resource.Error ->  MoviesListState(error = result.message ?: "An unexpected error")
                is Resource.Loading -> MoviesListState(isLoading = true)
                is Resource.Success -> MoviesListState(movies = result.data ?: emptyList())
            }
        }.launchIn(viewModelScope)
    }
}