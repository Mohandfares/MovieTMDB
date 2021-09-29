package com.dz.movietmdp.presentation.movies.components


import android.annotation.SuppressLint
import android.widget.RatingBar
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dz.movietmdp.R
import com.dz.movietmdp.domain.usecase.getmovies.MoviesFilter
import com.dz.movietmdp.domain.usecase.getmovies.TrendingFilter
import com.dz.movietmdp.presentation.Screen
import com.dz.movietmdp.presentation.common.EmptyStateUI
import com.dz.movietmdp.presentation.movies.MoviesViewModel
import com.dz.movietmdp.ui.theme.MatrixColor
import com.dz.movietmdp.ui.theme.MatrixColorAlpha
import com.dz.movietmdp.ui.theme.MatrixDarkColor


@Composable
fun MoviesScreen(
    navController: NavController,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Header()
                TrendingButtons(viewModel)
            }

            items(state.movies) { movie ->
                MovieListItem(
                    movieItem = movie,
                    onClickItem = {
                        navController.navigate(Screen.MovieDetailScreen.route + "/${movie.id}")
                    }
                )
            }

            item {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
            }
        }

        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(color = MatrixDarkColor),
            viewModel = viewModel,
        )

        if (state.error.isNotBlank()) {
            EmptyStateUI(
                error = state.error,
                modifier = Modifier.align(Alignment.Center),
                onClick = { viewModel.getMovies() }
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun Header() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(5.dp),
    ) {
        Card(shape = RoundedCornerShape(30.dp)) {
            Image(
                painter = painterResource(id = R.drawable.media),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(0.7f),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    text = "Welcome.",
                    style = MaterialTheme.typography.h3,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Millions of movies, TV shows and people to discover. Explore now.",
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun TrendingButtons(
    viewModel: MoviesViewModel,
) {
    val filter = viewModel.filterState.value
    if (filter.name == MoviesFilter.Trending.name) {
        val trending = viewModel.trendingState.value
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(5.dp)
        ) {
            Text(
                text = "Trending",
                style = MaterialTheme.typography.h5
            )

            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MatrixColor,
                        shape = RoundedCornerShape(100.dp)
                    )
            ) {
                Row {
                    Text(
                        text = "Today",
                        Modifier
                            .background(
                                color = if (trending.name == TrendingFilter.Day.name) MatrixColor else MatrixDarkColor,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .padding(10.dp)
                            .clickable { viewModel.trendingChange(TrendingFilter.Day) },
                        color = if (trending.name == TrendingFilter.Day.name) Color.White else MatrixColor
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "This week",
                        Modifier
                            .background(
                                color = if (trending.name == TrendingFilter.Week.name) MatrixColor else MatrixDarkColor,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .padding(10.dp)
                            .clickable { viewModel.trendingChange(TrendingFilter.Week) },
                        color = if (trending.name == TrendingFilter.Week.name) Color.White else MatrixColor
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    viewModel: MoviesViewModel,
) {
    val filterState = viewModel.filterState.value
    Column(modifier = modifier) {
        Divider()
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BottomNavigationItem(
                painter = painterResource(id = R.drawable.ic_twotone_explore_24),
                text = "Popular",
                filterState = filterState,
                onClickItem = { viewModel.filterChanged(MoviesFilter.Popular) })
            BottomNavigationItem(
                painter = painterResource(id = R.drawable.ic_twotone_star_24),
                text = "Rated",
                filterState = filterState,
                onClickItem = { viewModel.filterChanged(MoviesFilter.Rated) })
            BottomNavigationItem(
                painter = painterResource(id = R.drawable.ic_twotone_whatshot_24),
                text = "Trending",
                filterState = filterState,
                onClickItem = { viewModel.filterChanged(MoviesFilter.Trending) })
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun BottomNavigationItem(
    painter: Painter,
    text: String,
    filterState: MoviesFilter,
    onClickItem: () -> Unit
) {
    val color: Color = if (filterState.name == text) MatrixColor else Color.White
    val background = if (filterState.name == text) MatrixColorAlpha else MatrixDarkColor
    Column(
        modifier = Modifier
            .width(80.dp)
            .clickable { onClickItem() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(60.dp)
                .background(
                    color = background,
                    shape = RoundedCornerShape(30.dp)
                ),
        ) {
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .size(24.dp)
                    .align(Alignment.Center),
                colorFilter = ColorFilter.tint(color),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = text,
            color = color,
            fontSize = 11.sp
        )
    }
}