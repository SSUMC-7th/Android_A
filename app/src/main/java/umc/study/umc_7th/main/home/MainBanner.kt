package umc.study.umc_7th.main.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.Content
import umc.study.umc_7th.R
import umc.study.umc_7th.getTestMusicContentList
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class MainBannerProps<T: Content>(
    val title: String,
    val contentList: List<T>,
    val backgroundImage: ImageBitmap,
    val onContentClicked: (T) -> Unit,
    val onPlayButtonClicked: () -> Unit,
)

@OptIn(ExperimentalFoundationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainBanner(
    date: LocalDate,
    propsList: List<MainBannerProps<out Content>>,
    onVoiceSearchButtonClicked: () -> Unit,
    onSubscriptionButtonClicked: () -> Unit,
    onSettingButtonClicked: () -> Unit,
) {
    var buttonBarHeight by remember { mutableStateOf(0.dp) }
    val pageState = rememberPagerState { propsList.size }
    val density = LocalDensity.current

    Column {
        Box {
            HorizontalPager(
                state = pageState,
            ) { index ->
                MainBannerPage(
                    date = date,
                    prop = propsList[index],
                    upperPadding = buttonBarHeight
                )
            }
            // 버튼 바
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .onGloballyPositioned {
                        with(density) {
                            buttonBarHeight = it.size.height.toDp()
                        }
                    }
            ) {
                listOf(
                    onVoiceSearchButtonClicked to R.drawable.btn_main_mike,
                    onSubscriptionButtonClicked to R.drawable.btn_main_ticket,
                    onSettingButtonClicked to R.drawable.btn_main_setting,
                ).forEach { (onClick, icon) ->
                    IconButton(
                        onClick = onClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            tint = Color.White,
                        )
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            ),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            repeat(propsList.size) { index ->
                Spacer(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            color = if (index == pageState.currentPage)
                                Color.Blue
                            else
                                Color.Black.copy(alpha = 0.5f)
                        )
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun <T: Content> MainBannerPage(
    date: LocalDate,
    prop: MainBannerProps<T>,
    upperPadding: Dp,
) {
    Box {
        Image(
            bitmap = prop.backgroundImage,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(upperPadding))
            // 제목
            Text(
                text = prop.title,
                style = TextStyle(
                    color = Color.White,
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
                    onClick = prop.onPlayButtonClicked,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_panel_play_large),
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            }
            // 리스트 정보 축약
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "총 ${prop.contentList.size}곡", color = Color.White)
                Text(
                    text = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                    color = Color.White
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.heightIn(max = 100.dp)
            ) {
                items(count = prop.contentList.size) { index ->
                    val content = prop.contentList[index]
                    Button(
                        colors = ButtonColors(
                            containerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            contentColor = Color.White,
                            disabledContentColor = Color.White,
                        ),
                        onClick = { prop.onContentClicked(content) },
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                bitmap = content.imageBitmap,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(2.dp))
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Text(text = content.title, color = Color.White)
                                Text(text = content.author, color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewBanner() {
    MainBanner(
        date = LocalDate.parse("2019-11-11"),
        propsList = listOf(
            MainBannerProps(
                title = "포근하게 덮어주는 꿈의 목소리",
                contentList = getTestMusicContentList((1..4).random()),
                backgroundImage = ImageBitmap.imageResource(id = R.drawable.img_default_4_x_1),
                onContentClicked = {},
                onPlayButtonClicked = {},
            ),
            MainBannerProps(
                title = "달밤의 감성 산책",
                contentList = getTestMusicContentList((1..4).random()),
                backgroundImage = ImageBitmap.imageResource(id = R.drawable.img_jenre_exp_1),
                onContentClicked = {},
                onPlayButtonClicked = {},
            )
        ),
        onVoiceSearchButtonClicked = {},
        onSubscriptionButtonClicked = {},
        onSettingButtonClicked = {},
    )
}