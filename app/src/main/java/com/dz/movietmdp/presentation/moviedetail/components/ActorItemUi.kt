package com.dz.movietmdp.presentation.moviedetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dz.movietmdp.domain.model.Actor
import com.dz.movietmdp.ui.theme.MatrixColor
import com.dz.movietmdp.ui.theme.MatrixColorAlpha
import com.dz.movietmdp.R

@Composable
fun ActorItemUi(
    actor: Actor,
    onClickItem: (Actor) -> Unit
) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .background(color = MatrixColorAlpha, shape = RoundedCornerShape(10.dp))
            .clickable { onClickItem(actor) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .width(100.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Card(
                modifier = Modifier.size(width = 100.dp, height = 150.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Image(
                    painter = if (actor.profilePath != null)
                        rememberImagePainter(actor.profilePath)
                    else painterResource(id = R.drawable.notfoundimage),
                    contentDescription = "",
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = actor.name,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.padding(start = 3.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = actor.character,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                color = MatrixColor,
                maxLines = 1,
                modifier = Modifier.padding(start = 3.dp)
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}