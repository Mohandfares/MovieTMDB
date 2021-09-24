package com.dz.movietmdp.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dz.movietmdp.common.Constants.PARAM_MOVIE_ID
import com.dz.movietmdp.domain.model.MovieItem
import com.dz.movietmdp.presentation.moviedetail.components.MovieDetailScreen
import com.dz.movietmdp.presentation.movies.components.MovieItemUi
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
                            route = Screen.MovieDetailScreen.route + "/{${PARAM_MOVIE_ID}}"
                        ) {
                            MovieDetailScreen()
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true,uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    MovieTMDPTheme {
        //MoviesScreen(navController = rememberNavController())
    }
}