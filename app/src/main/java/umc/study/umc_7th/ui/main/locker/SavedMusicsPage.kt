package umc.study.umc_7th.ui.main.locker

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.R
import umc.study.umc_7th.SuspendedImage
import umc.study.umc_7th.previewMusicContentList

@Composable
fun SavedMusicsPage(
    musics: List<MusicContent>,
    onPlayAllButtonClicked: () -> Unit,
    onPlayButtonClicked: (MusicContent) -> Unit,
    onDeleteClicked: (MusicContent) -> Unit,
    onDetailsClicked: (MusicContent) -> Unit,
    onDeleteMusics: (List<MusicContent>) -> Unit,
) {
    val selected = remember(musics) { List(musics.size) { false }.toMutableStateList() }
    var isEditExpanded by remember { mutableStateOf(false) }
    var expandedIndex by remember(musics) { mutableStateOf<Int?>(null) }

    if (musics.isEmpty()) Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "저장한 곡이 없습니다.",
            style = TextStyle(
                color = Color.Black.copy(alpha = 0.5f),
            )
        )
    }
    else Column(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                TextButton(
                    onClick = {
                        val select = selected.any { !it }
                        selected.replaceAll { select }
                    },
                    contentPadding = PaddingValues(4.dp),
                ) {
                    val color = if (selected.any { !it })
                        Color.Black
                    else
                        Color.Blue

                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.btn_playlist_select_off),
                        contentDescription = null,
                        tint = color
                    )
                    Text(
                        text = "전체선택",
                        style = TextStyle(
                            color = color,
                            fontSize = 12.sp,
                        )
                    )
                }
                TextButton(
                    onClick = onPlayAllButtonClicked,
                    contentPadding = PaddingValues(4.dp),
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.btn_editbar_play),
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Text(
                        text = "전체듣기",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.Black,
                        )
                    )
                }
            }
            TextButton(
                onClick = { isEditExpanded = !isEditExpanded },
            ) {
                Text(
                    text = "편집",
                    style = TextStyle(color = Color.Blue)
                )
                DropdownMenu(
                    onDismissRequest = { isEditExpanded = false },
                    expanded = isEditExpanded,
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "삭제") },
                        onClick = {
                            onDeleteMusics(musics.filterIndexed { index, _ -> selected[index] })
                            isEditExpanded = false
                        },
                    )
                }
            }
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(
                count = musics.size,
            ) { index ->
                val music = musics[index]
                val checked = selected[index]
                MusicItem(
                    music = music,
                    isChecked = checked,
                    isExpanded = expandedIndex == index,
                    onPlayButtonClicked = { onPlayButtonClicked(music) },
                    onExpandButtonClicked = {
                        if (it) expandedIndex = index
                        else if (expandedIndex == index) expandedIndex = null
                    },
                    onDeleteClicked = { onDeleteClicked(music) },
                    onDetailsClicked = { onDetailsClicked(music) },
                    onClicked = { selected[index] = !checked },
                )
            }
        }
    }
}

@Composable
private fun MusicItem(
    music: MusicContent,
    isChecked: Boolean,
    isExpanded: Boolean,
    onClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit,
    onExpandButtonClicked: (Boolean) -> Unit,
    onDetailsClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
) {
    Box(
        modifier = Modifier.background(
            color = if (isChecked)
                Color.Black.copy(0.1f)
            else
                Color.Transparent
        ).clickable { onClicked() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            SuspendedImage(id = music.imageId) {
                if (it != null) Image(
                    bitmap = it,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = music.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = music.author,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black.copy(alpha = 0.5f),
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                IconButton(
                    onClick = onPlayButtonClicked,
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_player_play),
                        contentDescription = null,
                    )
                }
                IconButton(
                    onClick = { onExpandButtonClicked(!isExpanded) },
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_player_more),
                        contentDescription = null,
                    )
                    DropdownMenu(
                        onDismissRequest = { onExpandButtonClicked(false) },
                        expanded = isExpanded,
                    ) {
                        DropdownMenuItem(
                            text = { Text(text = "정보") },
                            onClick = { onDetailsClicked(); onExpandButtonClicked(false) },
                        )
                        DropdownMenuItem(
                            text = { Text(text = "삭제") },
                            onClick = { onDeleteClicked(); ; onExpandButtonClicked(false) },
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSavedMusicPage() {
    SavedMusicsPage(
        musics = previewMusicContentList,
        onPlayAllButtonClicked = {},
        onPlayButtonClicked = {},
        onDeleteClicked = {},
        onDetailsClicked = {},
        onDeleteMusics = {},
    )
}