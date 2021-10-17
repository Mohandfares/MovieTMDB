package com.dz.movietmdp.presentation.moviedetail.components

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
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
import androidx.navigation.NavController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.dz.movietmdp.R
import com.dz.movietmdp.common.Constants
import com.dz.movietmdp.common.runTime
import com.dz.movietmdp.common.toDateFormat
import com.dz.movietmdp.domain.model.MovieDetail
import com.dz.movietmdp.domain.model.ReviewItem
import com.dz.movietmdp.presentation.Screen
import com.dz.movietmdp.presentation.common.EmptyStateUI
import com.dz.movietmdp.presentation.moviedetail.MovieDetailViewModel
import com.dz.movietmdp.ui.theme.MatrixColor
import com.dz.movietmdp.ui.theme.MatrixDarkColor
import com.dz.movietmdp.ui.theme.YellowAlpha
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun MovieDetailScreen(
    navController: NavController,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        state.movieDetail?.let { movie ->
            val reviews: LazyPagingItems<ReviewItem> = viewModel.reviews.collectAsLazyPagingItems()
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
                    Overview(movieDetail = movie)
                }
                item {
                    if (movie.actors.isNotEmpty()) {
                        Text(
                            text = "Top Billed Cast",
                            style = typography.h5,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            items(movie.actors) { actor ->
                                ActorItemUi(
                                    actor = actor,
                                    onClickItem = {
                                        navController.navigate(Screen.ActorDetailScreen.route + "/${actor.id},${actor.creditId}")
                                    }
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                            }
                        }
                    }
                }

                item {
                    movie.director?.let { director ->
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Directing by",
                            style = typography.h5,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        ActorItemUi(
                            actor = director,
                            onClickItem = {
                                navController.navigate(Screen.ActorDetailScreen.route + "/${director.id},${director.creditId}")
                            }
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                    }
                }

                if (reviews.itemSnapshotList.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Reviews",
                            style = typography.h5,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }

                items(reviews) { review ->
                    ReviewItemUi(review!!)
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
    val image = if (movieDetail.backdropPath != null)
        rememberImagePainter(movieDetail.backdropPath)
    else painterResource(id = R.drawable.notfoundimage)
    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0f) }
    val height = remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Card(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)) {
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
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .align(Alignment.BottomStart)
                ) {
                    Text(
                        text = movieDetail.title,
                        style = typography.h5,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis
                    )
                    val date = movieDetail.releaseDate.toDateFormat()
                    val language = movieDetail.originalLanguage.uppercase()
                    val runtime = movieDetail.runtime.runTime()
                    val info = "$date ($language) . $runtime"
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = info,
                            fontWeight = FontWeight.Medium,
                            color = MatrixColor,
                            fontSize = 14.sp
                        )
                        MovieRating(movieDetail = movieDetail)
                    }

                }
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
        movieDetail.overview?.let { overview ->
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Overview",
                    style = typography.h5,
                    fontWeight = FontWeight.Bold
                )
                PlayTrailer(movieDetail.trailer)
            }
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

@Composable
fun MovieRating(movieDetail: MovieDetail) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        Text(
            text = "${movieDetail.voteAverage}",
            fontWeight = FontWeight.Bold,
            color = Color.Yellow,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(5.dp))
        val rating = (movieDetail.voteAverage / 2).toInt()
        for (i in 1..rating) {
            Image(
                painter = painterResource(id = R.drawable.ic_twotone_star_24),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Yellow),
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun PlayTrailer(trailer: String) {
    if (trailer.isNotBlank()) {
        val context = LocalContext.current
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(100.dp)
                )
                .padding(5.dp)
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailer))
                    startActivity(context, intent, null)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_play_arrow_24),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Play trailer",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}