package com.dz.movietmdp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.dz.movietmdp.common.Constants.PARAM_ACTOR
import com.dz.movietmdp.common.Constants.PARAM_MOVIE_ID
import com.dz.movietmdp.domain.model.Actor
import com.dz.movietmdp.presentation.actor.components.ActorScreen
import com.dz.movietmdp.presentation.moviedetail.components.MovieDetailScreen
import com.dz.movietmdp.presentation.movies.components.MoviesScreen
import com.dz.movietmdp.ui.theme.MovieTMDPTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieTMDPTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.MoviesScreen.route
                    ) {
                        composable(
                            route = Screen.MoviesScreen.route
                        ) {
                            MoviesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.MovieDetailScreen.route + "/{$PARAM_MOVIE_ID}"
                        ) {
                            MovieDetailScreen(navController = navController)
                        }
                        composable(
                            route = Screen.ActorDetailScreen.route + "/{$PARAM_ACTOR}"
                        ) {
                            ActorScreen()
                        }
                    }
                }
            }
        }
    }
}