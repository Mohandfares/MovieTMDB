package com.dz.movietmdp.presentation

sealed class Screen(val route: String) {
    object MoviesScreen : Screen("movies_screen")
}
