package com.dz.movietmdp.presentation.moviedetail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.End
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dz.movietmdp.R
import com.dz.movietmdp.common.reviewDateFormat
import com.dz.movietmdp.domain.model.ReviewItem
import com.dz.movietmdp.ui.theme.MatrixColor
import com.dz.movietmdp.ui.theme.MediumGray
import com.dz.movietmdp.ui.theme.MovieTMDPTheme
import com.dz.movietmdp.ui.theme.YellowAlpha

@Composable
fun ReviewItemUi(reviewItem: ReviewItem) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            RoundImage(
                reviewItem = reviewItem,
                modifier = Modifier
                    .size(56.dp)
                    .weight(3f)
            )
            Spacer(modifier = Modifier.width(30.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "A review by ${reviewItem.author}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    ReviewRating(rating = reviewItem.rating ?: 0.0)
                }
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = buildAnnotatedString {
                        val boldStyle = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                        val mediumGrayStyle = SpanStyle(
                            fontWeight = FontWeight.Medium,
                            color = MediumGray,
                            fontSize = 12.sp
                        )
                        pushStyle(mediumGrayStyle)
                        append("Written by ")
                        pop()
                        pushStyle(boldStyle)
                        append("${reviewItem.author} ")
                        pop()
                        pushStyle(mediumGrayStyle)
                        append(reviewItem.createdAt.reviewDateFormat())
                        pop()
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                val content = reviewItem.content
                val text = if (expanded) {
                    content
                } else {
                    if (content.length <= 200) {
                        content
                    } else {
                        "${content.substring(0..200)}..."
                    }
                }
                Text(
                    text = text,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun RoundImage(
    reviewItem: ReviewItem,
    modifier: Modifier = Modifier
) {
    val url = reviewItem.avatarPath ?: ""
    if (url.isNotBlank()) {
        Image(
            painter = rememberImagePainter(url),
            contentDescription = null,
            modifier = modifier
                .aspectRatio(1f, matchHeightConstraintsFirst = true)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                )
                .padding(3.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = modifier
                .background(
                    color = MatrixColor,
                    shape = CircleShape
                )
                .aspectRatio(1f, matchHeightConstraintsFirst = true)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = CircleShape
                )
                .padding(3.dp)
                .clip(CircleShape)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = reviewItem.author[0].uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun ReviewRating(rating: Double) {
    if (rating > 0.0) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .background(
                    color = YellowAlpha,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
            Text(
                text = "$rating",
                fontWeight = FontWeight.Bold,
                color = Color.Yellow,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_twotone_star_24),
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Yellow),
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    MovieTMDPTheme {
        ReviewItemUi(
            ReviewItem(
                id = "",
                avatarPath = null,
                author = "msbreviews",
                createdAt = "2014-11-12T16:06:04.927Z",
                rating = 10.0,
                content = "This is such a good movie, its got everything, a bit of sillyness, romance and action, the whole family really enjoyed it in fact the whole audience was laughing and clapping, not something you normally get in an Australian cinema. If you are debating what to watch at the cinemas watch this one its a blast and you wont be disappointed."
            )
        )
    }
}