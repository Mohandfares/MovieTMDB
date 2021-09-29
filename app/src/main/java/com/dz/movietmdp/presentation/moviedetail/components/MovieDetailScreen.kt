package com.dz.movietmdp.presentation.moviedetail.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.dz.movietmdp.R
import com.dz.movietmdp.common.toDateFormat
import com.dz.movietmdp.domain.model.MovieDetail
import com.dz.movietmdp.presentation.common.EmptyStateUI
import com.dz.movietmdp.presentation.moviedetail.MovieDetailViewModel
import com.dz.movietmdp.ui.theme.MatrixColor
import com.dz.movietmdp.ui.theme.MatrixDarkColor
import com.dz.movietmdp.ui.theme.YellowAlpha
import com.google.accompanist.flowlayout.FlowRow

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
                item {
                    movie.genres?.let { genres ->
                        FlowRow(
                            mainAxisSpacing = 10.dp,
                            crossAxisSpacing = 10.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            genres.forEach { genre ->
                                GenreItemUi(genre)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Overview(movieDetail = movie)
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
            .height(300.dp)
    ) {
        Card(shape = RoundedCornerShape(topStart = 20.dp,topEnd = 20.dp)) {
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
                        .fillMaxWidth()
                        .height(120.dp)
                        .align(Alignment.BottomStart)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MatrixDarkColor),
                                150.dp.value * 0.6F,
                                150.dp.value * 1F
                            )
                        )
                ) {}
                Text(
                    text = movieDetail.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(8.dp),
                    style = typography.h5,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun Overview(movieDetail: MovieDetail) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Released ${movieDetail.releaseDate.toDateFormat()}",
                fontWeight = FontWeight.Medium,
                color = MatrixColor,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .background(
                        color = YellowAlpha,
                        shape = RoundedCornerShape(80.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "${movieDetail.voteAverage}",
                    fontWeight = FontWeight.Bold,
                    color = Color.Yellow,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(5.dp))
                val rating = (movieDetail.voteAverage / 2).toInt()
                for (i in 1..rating) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_twotone_star_24),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.Yellow),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        movieDetail.overview?.let { overview ->
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Overview",
                style = typography.h5,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = overview,
                style = typography.body2
            )
        }

        movieDetail.homepage?.let { homepage ->
            val context = LocalContext.current
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = homepage,
                fontWeight = FontWeight.Medium,
                color = MatrixColor,
                fontSize = 12.sp,
                modifier = Modifier.clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(homepage))
                    startActivity(context, intent, null)
                }
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}
