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
import umc.study.umc_7th.Content
import umc.study.umc_7th.R
import umc.study.umc_7th.SuspendedImage
import umc.study.umc_7th.previewMusicContentList
import umc.study.umc_7th.previewPodcastContentList

@Composable
fun LikedContentsPage(
    contents: List<Content>,
    onPlayAllButtonClicked: () -> Unit,
    onPlayButtonClicked: (Content) -> Unit,
    onCancelLikeClicked: (Content) -> Unit,
    onDetailsClicked: (Content) -> Unit,
    onCancelLikesClicked: (List<Content>) -> Unit,
) {
    val selected = remember(contents) { List(contents.size) { false }.toMutableStateList() }
    var isEditExpanded by remember { mutableStateOf(false) }
    var expandedIndex by remember(contents) { mutableStateOf<Int?>(null) }

    if (contents.isEmpty()) Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "좋아요를 누른 콘텐츠가 없습니다.",
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
                        text = { Text(text = "좋아요 취소") },
                        onClick = {
                            onCancelLikesClicked(
                                contents.filterIndexed { index, _ -> selected[index] }
                            )
                            isEditExpanded = false
                        },
                    )
                }
            }
        }
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(
                count = contents.size,
            ) { index ->
                val content = contents[index]
                val checked = selected[index]
                ContentItem(
                    content = content,
                    isChecked = checked,
                    isExpanded = expandedIndex == index,
                    onPlayButtonClicked = { onPlayButtonClicked(content) },
                    onExpandButtonClicked = {
                        if (it) expandedIndex = index
                        else if (expandedIndex == index) expandedIndex = null
                    },
                    onCancelLikeClicked = { onCancelLikeClicked(content) },
                    onDetailsClicked = { onDetailsClicked(content) },
                    onClicked = { selected[index] = !checked },
                )
            }
        }
    }
}

@Composable
private fun ContentItem(
    content: Content,
    isChecked: Boolean,
    isExpanded: Boolean,
    onClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit,
    onExpandButtonClicked: (Boolean) -> Unit,
    onDetailsClicked: () -> Unit,
    onCancelLikeClicked: () -> Unit,
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
            SuspendedImage(id = content.imageId) {
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
                    text = content.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = content.author,
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
                            text = { Text(text = "좋아요 취소") },
                            onClick = { onCancelLikeClicked(); onExpandButtonClicked(false) },
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLikedContentsPage() {
    LikedContentsPage(
        contents = previewMusicContentList + previewPodcastContentList,
        onPlayAllButtonClicked = {},
        onPlayButtonClicked = {},
        onCancelLikeClicked = {},
        onDetailsClicked = {},
        onCancelLikesClicked = {},
    )
}