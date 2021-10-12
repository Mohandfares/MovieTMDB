package com.dz.movietmdp.presentation.actor.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.dz.movietmdp.R
import com.dz.movietmdp.domain.model.*
import com.dz.movietmdp.presentation.actor.ActorViewModel
import com.dz.movietmdp.presentation.common.EmptyStateUI
import com.dz.movietmdp.ui.theme.BlackShadow
import com.dz.movietmdp.ui.theme.MatrixColor
import com.dz.movietmdp.ui.theme.MatrixColorAlpha
import com.dz.movietmdp.ui.theme.MatrixDarkColor

@Composable
fun ActorScreen(
    viewModel: ActorViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Box(Modifier.fillMaxSize()) {
        state.actorDetail?.let { actor ->
            LazyColumn(
                contentPadding = PaddingValues(5.dp)
            ) {
                item {
                    Poster(actor)
                }
                item {
                    if (actor.biography.isNotBlank()) {
                        Text(
                            text = "Biography",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = actor.biography,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                item {
                    if (actor.knownFor.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "KnownFor",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            items(actor.knownFor) { movie ->
                                ActorMovieItem(movie)
                                Spacer(modifier = Modifier.width(3.dp))
                            }
                        }
                    }
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
fun Poster(actorDetail: ActorDetail) {
    val image = if (actorDetail.posterPath != null)
        rememberImagePainter(actorDetail.posterPath)
    else painterResource(id = R.drawable.notfoundimage)
    val density = LocalDensity.current.density
    val width = remember { mutableStateOf(0f) }
    val height = remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
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
                        .height(100.dp)
                        .align(Alignment.BottomStart)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, MatrixDarkColor),
                                180.dp.value * 0.2F,
                                180.dp.value * 1F
                            )
                        )
                ) {}
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                ) {
                    Text(
                        text = actorDetail.name,
                        style = MaterialTheme.typography.h5,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis
                    )
                    SocialLinks(socialLinks = actorDetail.socialLinks)
                }
            }
        }
    }
}

@Composable
fun ActorMovieItem(
    movieItem: MovieItem,
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .background(color = MatrixColorAlpha, shape = RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .width(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                modifier = Modifier.size(width = 100.dp, height = 150.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Image(
                    painter = if (movieItem.posterPath != null)
                        rememberImagePainter(movieItem.posterPath)
                    else painterResource(id = R.drawable.notfoundimage),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = movieItem.originalTitle,
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.padding(start = 3.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
fun SocialLinks(socialLinks: SocialLinks) {
    if (!socialLinks.empty) {
        val context = LocalContext.current
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.padding(10.dp)
        ) {
            socialLinks.facebook?.let {
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/${socialLinks.facebook}")
                            )
                            ContextCompat.startActivity(context, intent, null)
                        }
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            socialLinks.instagram?.let {
                Image(
                    painter = painterResource(id = R.drawable.instagram),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.instagram.com/${socialLinks.instagram}")
                            )
                            ContextCompat.startActivity(context, intent, null)
                        }
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
            socialLinks.twitter?.let {
                Image(
                    painter = painterResource(id = R.drawable.twitter),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.White),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://twitter.com/${socialLinks.twitter}")
                            )
                            ContextCompat.startActivity(context, intent, null)
                        }
                )
            }
        }
    }
}

