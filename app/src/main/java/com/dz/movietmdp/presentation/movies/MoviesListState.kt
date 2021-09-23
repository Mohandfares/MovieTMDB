package com.dz.movietmdp.presentation.movies

import com.dz.movietmdp.domain.model.MovieItem

data class MoviesListState(
    val isLoading: Boolean = false,
    val movies: List<MovieItem> = emptyList(),
    val error: String = ""
)
