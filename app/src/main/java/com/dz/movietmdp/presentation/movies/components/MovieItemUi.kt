package com.dz.movietmdp.presentation.movies.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dz.movietmdp.domain.model.MovieItem

@Composable
fun MovieItemUi(movieItem: MovieItem) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.width(200.dp).padding(5.dp)
    ) {
        Card(
            modifier = Modifier.size(width = 200.dp,height = 250.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Image(
                painter = rememberImagePainter(movieItem.posterPath),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = movieItem.originalTitle,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = movieItem.releaseDate,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp
        )
    }
}