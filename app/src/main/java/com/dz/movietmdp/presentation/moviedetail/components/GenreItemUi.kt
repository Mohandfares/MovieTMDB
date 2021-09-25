package com.dz.movietmdp.presentation.moviedetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dz.movietmdp.data.remote.dto.Genre
import com.dz.movietmdp.ui.theme.MatrixColorAlpha

@Composable
fun GenreItemUi(genre: Genre) {
    Box(
        modifier = Modifier
            .background(
                color = MatrixColorAlpha,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(10.dp)
    ) {
        Text(
            text = genre.name,
            color = Color.White,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
        )
    }
}