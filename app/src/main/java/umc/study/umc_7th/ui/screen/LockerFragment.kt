package umc.study.umc_7th.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import umc.study.umc_7th.R
import umc.study.umc_7th.ui.composables.MiniPlayer
import umc.study.umc_7th.ui.theme.Purple40
import umc.study.umc_7th.ui.viewmodel.MockMusicViewModel
import umc.study.umc_7th.ui.viewmodel.MusicViewModel

@Composable
fun LockerFragment(viewModel: MusicViewModel) {

    val selectedTabIndex = remember { mutableStateOf(0) }
    val tabTitles = listOf("저장한 곡", "음악 파일", "저장 앨범")

    val likedAlbums = viewModel.getLikedAlbums().observeAsState(emptyList()).value
    val selectedAlbums = remember { mutableStateOf(likedAlbums.map { it.id to false }.toMap()) }
    val onSelectAllClickAlbums: () -> Unit = {
        val allSelected = likedAlbums.all { selectedAlbums.value[it.id] == true }
        selectedAlbums.value = likedAlbums.associate { it.id to !allSelected }
    }
    // Get list of liked songs from the view model
    val likedSongs = viewModel.getLikedSongs().observeAsState(emptyList()).value
    // Track the selected state for each item
    val selectedSongs = remember { mutableStateOf(likedSongs.map { it.id to false }.toMap()) }
    // Handle select all functionality
    val onSelectAllClickSongs: () -> Unit = {
        val allSelected = likedSongs.all { selectedSongs.value[it.id] == true }
        // Toggle all items based on current state
        selectedSongs.value = likedSongs.associate { it.id to !allSelected }
    }

    val context = LocalContext.current
    val album_info = context.getString(R.string.album_info)

    Scaffold(
        topBar = {
            Column {
                Row {
                    Text(
                        text = "보관함",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Purple40
                        )
                    )
                    {
                        Text(
                            text = "로그인",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                TabRow(
                    selectedTabIndex = selectedTabIndex.value,
                    modifier = Modifier.padding(end = 130.dp)
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex.value == index,
                            onClick = { selectedTabIndex.value = index },
                            text = { Text(title) },
                            modifier = Modifier.wrapContentWidth()
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onSelectAllClickSongs) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.btn_playlist_select_off
                            ),
                            contentDescription = "selectAll"
                        )
                    }
                    Text("전체 선택")
                    IconButton(onClick = { /* Play All Songs */ }) {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.icon_browse_arrow_right
                            ),
                            contentDescription = "playAll"
                        )
                    }
                    Text("전체 듣기")
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /* Edit Songs */ }) {
                        Text("편집")
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column {
                MiniPlayer(viewModel = viewModel, progress = 0f)
            }
        }
    ) { innerPadding ->
        when (selectedTabIndex.value) {
            0 -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (likedSongs.isEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(200.dp))
                            Text(
                                text = "저장한 곡이 없습니다.",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            )
                            Spacer(modifier = Modifier.height(150.dp))
                        }
                    }
                    itemsIndexed(likedSongs) { _, song ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Song Title
                            Text(
                                text = song.title,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                            // Like/Unlike IconButton
                            IconButton(
                                onClick = { viewModel.toggleLikeStatus(song) }
                            ) {
                                Icon(
                                    imageVector = if (song.isLike) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = if (song.isLike) "Unlike" else "Like",
                                    tint = if (song.isLike) Color.Red else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
            2 -> {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    if (likedAlbums.isEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(200.dp))
                            Text(
                                text = "저장한 앨범이 없습니다.",
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            )
                            Spacer(modifier = Modifier.height(150.dp))
                        }
                    }
                    itemsIndexed(likedAlbums) { index, album ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(painter = painterResource(
                                id = album.coverImg),
                                contentDescription = "Album Cover",
                                modifier = Modifier.size(100.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = album.title,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = album.singer,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = album_info,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Row {
                                IconButton(onClick = { /* Play 버튼 클릭 시 액션 */ }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.btn_player_play),
                                        contentDescription = "Play"
                                    )
                                }
                                IconButton(onClick = { /* More 버튼 클릭 시 액션 */ }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.btn_player_more),
                                        contentDescription = "More"
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun LockerFragmentPreview() {
    val viewModel = MockMusicViewModel()
    LockerFragment(viewModel)
}