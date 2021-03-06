package com.dz.movietmdp.presentation.movies.components


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
    val totalPages = state.totalPages
    val currentPage = viewModel.pageState.value
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Header(viewModel)
                TrendingButtons(viewModel)
                Spacer(modifier = Modifier.height(5.dp))
                if (totalPages > 5) {
                    val pages = listOf(0, 1, 2, 3, 4, 5, 6)
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items(pages) { page ->
                            when (page) {
                                0 -> PageItem(
                                    back = true,
                                    currentPage = currentPage,
                                    onClickItem = { viewModel.loadBackMovies() })
                                6 -> PageItem(
                                    next = true,
                                    currentPage = currentPage,
                                    onClickItem = {
                                        if (currentPage < totalPages)
                                            viewModel.loadMoreMovies()
                                    })
                                else -> PageItem(
                                    value = page,
                                    currentPage = currentPage,
                                    onClickItem = { viewModel.loadMoreMovies(page = page) })
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                    }
                } else if (totalPages in 2..5) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items((1..totalPages).toList()) { page ->
                            PageItem(
                                value = page,
                                currentPage = currentPage,
                                onClickItem = { viewModel.loadMoreMovies(page = page) })
                            Spacer(modifier = Modifier.width(5.dp))
                        }
                    }
                }
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
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 120.dp),
                onClick = { viewModel.getMovies() }
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (state.movies.isEmpty() && state.error.isEmpty()) {
            EmptyStateUI(
                error = stringResource(id = R.string.movies_list_empty),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 120.dp),
                tryAgain = false
            )
        }
    }
}

@Composable
fun Header(viewModel: MoviesViewModel) {
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
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = "Welcome.",
                    style = MaterialTheme.typography.h3,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(10.dp))
                val filter: MoviesFilter = viewModel.filterState.value
                if (filter.name == MoviesFilter.Search.name) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = MatrixColor,
                                shape = RoundedCornerShape(100.dp)
                            )
                            .background(
                                color = MatrixColorAlpha,
                                shape = RoundedCornerShape(100.dp)
                            )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                                contentDescription = "",
                                modifier = Modifier.size(24.dp),
                                colorFilter = ColorFilter.tint(color = Color.White)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            val query = viewModel.queryState.value.replace("''", "")
                            var text by remember { mutableStateOf<String>(query) }
                            Box {
                                BasicTextField(
                                    value = query,
                                    onValueChange = {
                                        viewModel.doSearch(it)
                                        text = it
                                    },
                                    maxLines = 1,
                                    textStyle = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Text(
                                    text = if (text.isEmpty()) stringResource(id = R.string.search_hint) else "",
                                    modifier = Modifier.fillMaxWidth(),
                                )
                            }
                        }
                    }
                } else {
                    Text(
                        text = "Millions of movies, TV shows and people to discover. Explore now.",
                        style = MaterialTheme.typography.body1,
                        color = Color.White
                    )
                }
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
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
        Divider(Modifier.height(0.5.dp))
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
            BottomNavigationItem(
                painter = painterResource(id = R.drawable.ic_baseline_youtube_searched_for_24),
                text = "Search",
                filterState = filterState,
                onClickItem = { viewModel.filterChanged(MoviesFilter.Search) })
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

@Composable
fun PageItem(
    value: Int = 0,
    next: Boolean = false,
    back: Boolean = false,
    currentPage: Int,
    onClickItem: () -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MatrixColor,
                shape = RoundedCornerShape(100.dp)
            )
            .clickable { onClickItem() }
    ) {
        Text(
            text = if (next && currentPage < 6) "Next" else if (next && currentPage >= 6) "Page $currentPage" else if (back) "Back" else "$value",
            Modifier
                .background(
                    color = if (currentPage == value && !next || currentPage > value && next || back) MatrixColorAlpha else MatrixDarkColor,
                    shape = RoundedCornerShape(100.dp)
                )
                .padding(horizontal = 11.dp, vertical = 5.dp),
            color = if (currentPage == value && !next || currentPage > value && next || back) Color.White else MatrixColor
        )
    }
}