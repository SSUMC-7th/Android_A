package umc.study.umc_7th.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import umc.study.umc_7th.data.model.Content
import umc.study.umc_7th.R
import umc.study.umc_7th.ui.viewmodel.MockMusicViewModel
import umc.study.umc_7th.ui.viewmodel.MusicViewModel

enum class GlobeCategory(
    val expression: String,
) {
    GLOBAL(expression = "종합"),
    DOMESTIC(expression = "국내"),
    FOREIGN(expression = "해외"),
}

@Composable
fun GlobeCategorizedMusicCollectionView(
    title: String,
    viewModel: MusicViewModel,
    globeCategory: GlobeCategory,
    onViewTitleClicked: () -> Unit,
    onContentClicked: (Content) -> Unit,
    onCategoryClicked: (GlobeCategory) -> Unit
) {

    // LiveData를 observe하여 Album과 Song 데이터 가져오기
    val album by viewModel.album.observeAsState(emptyList())
    val currentSongIndex by viewModel.currentSongIndex.observeAsState(0)

    LaunchedEffect(Unit) {
        // 전체 Album 데이터를 로드
        viewModel.loadAllAlbums()
    }

    ContentCollectionView(
        contentList = album.map { album ->
            Content(
                title = album.title,
                singer = album.singer,
                image = ImageBitmap.imageResource(id = album.coverImg),
                length = 0
            )
        },
        titleBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable { onViewTitleClicked() },
                ) {
                    Text(
                        text = title,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.btn_arrow_more),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    GlobeCategory.entries.forEach { category ->
                        ClickableText(
                            text = AnnotatedString(category.expression),
                            onClick = { onCategoryClicked(category) },
                            style = TextStyle(
                                color = if (category == globeCategory) Color.Blue else Color.Unspecified
                            )
                        )
                    }
                }
            }
        },
        thumbnail = { content ->
            Box(
                contentAlignment = Alignment.BottomEnd,
            ) {
                Image(
                    bitmap = content.image,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                IconButton(onClick = { viewModel.getFirstSong() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_miniplayer_play),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        },
        onContentClicked = onContentClicked,
    )
}

@Composable
fun PodcastCollectionView(
    title: String,
    contentList: List<Content>,
    onContentClicked: (Content) -> Unit,
) {
    ContentCollectionView(
        contentList = contentList,
        titleBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        },
        thumbnail = { content ->
            Image(
                bitmap = content.image,
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        },
        onContentClicked = onContentClicked,
    )
}

@Composable
fun VideoCollectionView(
    title: String,
    contentList: List<Content>,
    onContentClicked: (Content) -> Unit,
) {
    ContentCollectionView(
        contentList = contentList,
        titleBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        },
        thumbnail = { content ->
            Box(contentAlignment = Alignment.BottomEnd) {
                Image(
                    bitmap = content.image,
                    contentScale = ContentScale.FillHeight,
                    contentDescription = null,
                    modifier = Modifier
                        .width(228.dp)
                        .height(128.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
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
        onContentClicked = onContentClicked,
    )
}

@Composable
private fun ContentCollectionView(
    contentList: List<Content>,
    titleBar: @Composable () -> Unit, // 제목 바에 그려질 컴포저블
    thumbnail: @Composable (Content) -> Unit, // 컨텐츠 미리보기 사진 컴포저블
    onContentClicked: (Content) -> Unit
) {
    val density = LocalDensity.current
    var contentWidth by remember { mutableStateOf(0.dp) }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        titleBar()
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item { Spacer(modifier = Modifier.width(0.dp)) }
            items(count = contentList.size) { index ->
                val content = contentList[index]
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.clickable { onContentClicked(content) }
                ) {
                    Box(
                        modifier = Modifier.onGloballyPositioned {
                            with(density) { contentWidth = it.size.width.toDp() }
                        }
                    ) {
                        thumbnail(content)
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.width(contentWidth)
                    ) {
                        Text(
                            text = content.title,
                            style = TextStyle(
                                fontSize = 16.sp,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = content.singer,
                            style = TextStyle(
                                color = TextStyle.Default.color.copy(alpha = 0.5f)
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.width(0.dp)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGlobeCategorizedMusicCollectionView() {
    GlobeCategorizedMusicCollectionView(
        title = "오늘 발매 음악",
        viewModel = MockMusicViewModel(),
        globeCategory = GlobeCategory.GLOBAL,
        onViewTitleClicked = {},
        onContentClicked = {},
        onCategoryClicked = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPodcastCollectionView() {
    PodcastCollectionView(
        title = "매일 들어도 좋은 팟캐스트",
        contentList = List(15) {
            Content(
                title = "김시선의 귀책사유 FLO X 윌라",
                singer = "김시선",
                image = ImageBitmap.imageResource(id = R.drawable.img_potcast_exp),
                length = 200,
            )
        },
        onContentClicked = {},
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewVideoCollectionView() {
    VideoCollectionView(
        title = "비디오 콜렉션",
        contentList = List(15) {
            Content(
                title = "제목",
                singer = "지은이",
                image = ImageBitmap.imageResource(id = R.drawable.img_video_exp),
                length = 200,
            )
        },
        onContentClicked = {},
    )
}