package com.dz.movietmdp.presentation.moviedetail

import com.dz.movietmdp.domain.model.MovieDetail

data class MovieState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val error: String = ""
)