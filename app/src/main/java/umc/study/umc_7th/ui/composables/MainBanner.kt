package umc.study.umc_7th.ui.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.Content
import umc.study.umc_7th.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainBanner(
    title: String,
    date: LocalDate,
    contentList: List<Content>,
    textColor: Color,
    backgroundImage: ImageBitmap,
    onVoiceSearchButtonClicked: () -> Unit,
    onSubscriptionButtonClicked: () -> Unit,
    onSettingButtonClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit,
) {

    val pagerState = rememberPagerState(pageCount = { 7 })
    val scope = rememberCoroutineScope()
    val autoSlideInterval = 3000L
    val pageCount = 7

    LaunchedEffect(pagerState) {
        while (true) {
            // Delay for the specified interval
            kotlinx.coroutines.delay(autoSlideInterval)

            // Navigate to the next page
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % pageCount)
        }
    }

    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Column {
                Box {
                    Image(
                        // background image
                        bitmap = backgroundImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop, // 화면에 이미지 꽉 채우는 용도
                        modifier = Modifier.matchParentSize(),
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // 우측 상단의 버튼 바
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            listOf(
                                onVoiceSearchButtonClicked to R.drawable.btn_main_mike,
                                onSubscriptionButtonClicked to R.drawable.btn_main_ticket,
                                onSettingButtonClicked to R.drawable.btn_main_setting,
                            ).forEach { (onClick, icon) ->
                                Icon(
                                    painter = painterResource(id = icon),
                                    contentDescription = null,
                                    tint = textColor,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clickable { onClick() }
                                )
                            }
                        }
                        // 제목
                        Text(
                            text = title,
                            style = TextStyle(
                                color = textColor,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                lineBreak = LineBreak.Heading,
                            )
                        )
                        // 전체 재생 버튼
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = onPlayButtonClicked,
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.btn_panel_play_large),
                                    contentDescription = null,
                                    tint = textColor,
                                )
                            }
                        }
                        // 리스트 정보 축약
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(text = "총 ${contentList.size}곡", color = textColor)
                            Text(
                                text = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                                color = textColor
                            )
                        }
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.height(100.dp)
                        ) {
                            items(count = contentList.size) { index ->
                                val content = contentList[index]
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    modifier = Modifier.clickable { /* 기능 구현 */ }
                                ) {
                                    Image(
                                        bitmap = content.image,
                                        contentDescription = null,
                                        modifier = Modifier.size(32.dp)
                                    )
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(4.dp),
                                    ) {
                                        Text(text = content.title, color = textColor)
                                        Text(text = content.author, color = textColor)
                                    }
                                }
                            }
                        }
                    }
                }
                CircleIndicator(
                    pageCount = pageCount,
                    selectedIndex = pagerState.currentPage,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun CircleIndicator(
    pageCount: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .size(20.dp) // Size of the circle
                    .padding(4.dp) // Padding between circles
                    .clip(CircleShape) // Make it circular
                    .background(
                        color = if (index == selectedIndex) Color.Blue else Color.Gray // Change color based on selection
                    )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewBanner() {
    MainBanner(
        title = "포근하게 덮어주는 꿈의 목소리",
        date = LocalDate.parse("2019-11-11"),
        contentList = List(15) {
            Content(
                title = "Butter",
                author = "BTS",
                image = ImageBitmap.imageResource(id = R.drawable.img_album_exp),
                length = 200,
            )
        },
        textColor = Color.White,
        backgroundImage = ImageBitmap.imageResource(id = R.drawable.img_default_4_x_1),
        onVoiceSearchButtonClicked = {},
        onSubscriptionButtonClicked = {},
        onSettingButtonClicked = {},
        onPlayButtonClicked = {},
    )
}