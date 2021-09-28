package com.dz.movietmdp.presentation.movies.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dz.movietmdp.R
import com.dz.movietmdp.domain.model.MovieItem
import com.dz.movietmdp.ui.theme.*

@Composable
fun MovieListItem(movieItem: MovieItem,onClickItem: (MovieItem) -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .clickable { onClickItem(movieItem) }
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomStart)
                .background(
                    color = MatrixColorAlpha02,
                    shape = RoundedCornerShape(10.dp)
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Card(
                modifier = Modifier.size(width = 150.dp,height = 200.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Image(
                    painter = rememberImagePainter(movieItem.posterPath),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column(verticalArrangement = Arrangement.Bottom) {
                Text(
                    text = movieItem.originalTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = movieItem.releaseDate,
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp,
                        color = MediumGray
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .background(
                                color = YellowAlpha,
                                shape = RoundedCornerShape(60.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "${movieItem.voteAverage}",
                            fontWeight = FontWeight.Bold,
                            color = Color.Yellow,
                            fontSize = 12.sp
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_twotone_star_24),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.Yellow),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }


    }
}

@Preview
@Composable
fun Preview() {
    MovieTMDPTheme() {
        MovieListItem(
            movieItem = MovieItem(123,"Titanike",8.9,"","2015-05-13"),
            onClickItem = {})
    }
}