package umc.study.umc_7th.ui.main.album

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.R
import umc.study.umc_7th.previewMusicContentList

@Composable
fun IncludedMusicsPage(
    contentList: List<MusicContent>,
    isMixed: Boolean,
    onPlayContentClicked: (MusicContent) -> Unit,
    onContentDetailsClicked: (MusicContent) -> Unit,
    onMixButtonClicked: (Boolean) -> Unit,
    onPlayAllButtonClicked: () -> Unit,
) {
    val isContentSelected = remember(contentList) {
        List(contentList.size) { false }.toMutableStateList()
    }

    Column {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(8.dp),
        ) {
            // 내 취향 MIX
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(
                        color = Color.Black.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(percent = 50)
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = "내 취향 MIX",
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                IconButton(
                    onClick = { onMixButtonClicked(!isMixed) },
                    modifier = Modifier.height(16.dp)
                ) {
                    Image(
                        painter = painterResource(
                            id = if (isMixed)
                                R.drawable.btn_toggle_on
                            else
                                R.drawable.btn_toggle_off
                        ),
                        contentDescription = null,
                        modifier = Modifier.height(32.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            // 전체선택과 전체듣기
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(percent = 50))
                        .clickable {
                            val isAllSelected = isContentSelected.all { it }
                            isContentSelected.replaceAll { !isAllSelected }
                        }
                ) {
                    Image(
                        painter = painterResource(
                            id = if (isContentSelected.any { !it })
                                R.drawable.btn_playlist_select_off
                            else
                                R.drawable.btn_playlist_select_on
                        ),
                        contentScale = ContentScale.Fit,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "전체선택",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = if (isContentSelected.any { !it })
                                Color.Unspecified
                            else
                                Color.Blue

                        ),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(percent = 50))
                        .clickable { onPlayAllButtonClicked() }
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.btn_editbar_play
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "전체듣기",
                        style = TextStyle(
                            fontSize = 12.sp,
                        ),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }
        }
        // 목록
        LazyColumn(
            modifier = Modifier.heightIn(max = 360.dp)
        ) {
            items(
                count = contentList.size,
            ) { index ->
                val content = contentList[index]
                val selected = isContentSelected[index]
                MusicItem(
                    music = content,
                    index = index,
                    isSelected = selected,
                    onClicked = { isContentSelected[index] = !selected },
                    onPlayButtonClicked = { onPlayContentClicked(content) },
                    onDetailsButtonClicked = { onContentDetailsClicked(content) },
                )
            }
        }
    }
}

@Composable
private fun MusicItem(
    music: MusicContent,
    index: Int,
    isSelected: Boolean,
    onClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit,
    onDetailsButtonClicked: () -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected)
                    Color.Black.copy(0.1f)
                else
                    Color.Transparent
            )
            .clickable { onClicked() }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp),
                ) {
                    Text(
                        text = "%02d".format(index + 1),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(24.dp)
                    ) {
                        music.label?.let { label ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = Color.Blue,
                                        shape = RoundedCornerShape(percent = 50)
                                    )
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = label,
                                    style = TextStyle(
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    ),
                                )
                            }
                        }
                        Text(
                            text = music.title,
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                            modifier = Modifier.weight(1f),
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.height(20.dp),
                    ) {
                        Text(
                            text = music.author,
                            style = TextStyle(
                                color = Color.Black.copy(0.5f),
                                fontSize = 12.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = onPlayButtonClicked,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_player_play),
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = onDetailsButtonClicked,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_player_more),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewIncludedContentsPage() {
    IncludedMusicsPage(
        contentList = previewMusicContentList,
        isMixed = false,
        onPlayContentClicked = {},
        onContentDetailsClicked = {},
        onMixButtonClicked = {},
        onPlayAllButtonClicked = {},
    )
}