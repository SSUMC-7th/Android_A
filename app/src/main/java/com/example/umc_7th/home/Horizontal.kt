package com.example.umc_7th.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

enum class BaseLocationCategory(val mean: String) {
    GLOBAL(mean = "종합"),
    DOMESTIC(mean = "국내"),
    FOREIGN(mean = "해외"),
}

@Composable
fun horizontalScrollContentView(
    contentList: List<Content>,
    titleBar: @Composable () -> Unit,
    thumbnail: @Composable (Content) -> Unit,
    contentClick: (Content) -> Unit
) {
    Column {
        titleBar()
        LazyRow {
            items(contentList) { content ->
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { contentClick(content) }
                ) {
                    thumbnail(content)
                }
            }
        }
    }
}

@Composable
fun LocationMusicContentView(
    title: String,
    contentList: List<Content>,
    baseLocationCategory: BaseLocationCategory,
    viewTitleClick: () -> Unit,
    contentClick: (Content) -> Unit,
    categoryClick: (BaseLocationCategory) -> Unit,
) {
    horizontalScrollContentView(
        contentList = contentList,
        titleBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 5.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable { viewTitleClick() }
                ) {
                    Text(
                        text = title,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.btn_main_arrow_more),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    BaseLocationCategory.entries.forEach { category ->
                        ClickableText(
                            text = AnnotatedString(category.mean),
                            onClick = { categoryClick(category) },
                            style = TextStyle(
                                color = if (category == baseLocationCategory) Color.Blue else Color.Unspecified
                            )
                        )
                    }
                }
            }
        },
        thumbnail = { content ->
            Box(contentAlignment = Alignment.BottomEnd) {
                content.image?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(128.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.btn_miniplayer_play),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        },
        contentClick = contentClick,
    )
}

@Composable
fun PodcastCollectionView(
    title: String,
    contentList: List<Content>,
    contentClick: (Content) -> Unit,
) {
    horizontalScrollContentView(
        contentList = contentList,
        titleBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        },
        thumbnail = { content ->
            content.image?.let {
                Image(
                    painter = painterResource(id = it),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        },
        contentClick = contentClick,
    )
}

@Composable
fun VideoCollectionView(
    title: String,
    contentList: List<Content>,
    contentClick: (Content) -> Unit,
) {
    horizontalScrollContentView(
        contentList = contentList,
        titleBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        },
        thumbnail = { content ->
            Box(contentAlignment = Alignment.BottomEnd) {
                content.image?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .width(228.dp)
                            .height(128.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(color = Color.Black.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "${"%02d".format(content.length / 60)}:${content.length % 60}",
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = Color.White,
                        ),
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }
        },
        contentClick = contentClick
    )
}





