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

    private val _pageState = mutableStateOf<Int>(1)
    val pageState: State<Int> = _pageState

    init {
        getMovies()
    }

    fun filterChanged(filter: MoviesFilter) {
        _filterState.value = filter
        _pageState.value = 1
        getMovies()
    }

    fun trendingChange(trending: TrendingFilter) {
        _trendingState.value = trending
        _pageState.value = 1
        getMovies()
    }

    fun loadMoreMovies(page: Int = 0) {
        _pageState.value = if (page == 0) _pageState.value + 1 else page
        getMovies()
    }

    fun getMovies(
        filter: MoviesFilter = filterState.value,
        trending: TrendingFilter = trendingState.value,
        page: Int = pageState.value
    ) {
        moviesUseCase(filter,trending,page).onEach { result ->
            _state.value = when (result) {
                is Resource.Error -> MoviesListState(error = result.message ?: "An unexpected error")
                is Resource.Loading -> MoviesListState(isLoading = true)
                is Resource.Success -> MoviesListState(
                    movies = result.data?.results ?: emptyList(),
                    totalPages = result.data?.totalPages ?: 0
                )
            }
        }.launchIn(viewModelScope)
    }
}