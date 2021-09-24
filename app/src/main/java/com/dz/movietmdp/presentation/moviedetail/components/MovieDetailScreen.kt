package com.dz.movietmdp.presentation.moviedetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.dz.movietmdp.R
import com.dz.movietmdp.domain.model.MovieDetail
import com.dz.movietmdp.presentation.common.EmptyStateUI
import com.dz.movietmdp.presentation.moviedetail.MovieDetailViewModel
import androidx.compose.ui.platform.LocalDensity
import com.dz.movietmdp.common.Constants
import com.dz.movietmdp.domain.model.MovieItem
import com.dz.movietmdp.presentation.movies.components.MovieItemUi

@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        state.movieDetail?.let { movie ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(5.dp)
            ) {
                item { 
                    Cover(movieDetail = movie)
                }
            }
        }

        if (state.error.isNotBlank()) {
            EmptyStateUI(
                error = state.error,
                modifier = Modifier.align(Alignment.Center),
                onClick = { viewModel.tryAgain() }
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun Cover(movieDetail: MovieDetail) {
    val image = rememberImagePainter(movieDetail.backdropPath)
    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0f) }
    val height = remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(5.dp),
    ) {
        Card(shape = RoundedCornerShape(30.dp)) {
            Box {
                Image(
                    painter = image,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .onGloballyPositioned {
                            width.value = it.size.width / density
                            height.value = it.size.height / density
                        },
                    contentScale = ContentScale.Crop,
                )
                Column(
                    Modifier
                        .fillMaxWidth().height(120.dp).align(Alignment.BottomStart)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, Color.Black),
                                250 * 0.6F,
                                250 * 1F
                            )
                        )
                ) {}
                Text(
                    text = movieDetail.title,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    style = typography.body2,
                    color = Color.White
                )
            }
        }
    }
}