package umc.study.umc_7th.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import umc.study.umc_7th.R
import umc.study.umc_7th.ui.composables.MiniPlayer
import umc.study.umc_7th.ui.theme.Purple40
import umc.study.umc_7th.ui.viewmodel.MockMusicViewModel

@Composable
fun LockerFragment() {

    var selectedTabIndex by remember { mutableStateOf(0) }

    val mockViewModel = MockMusicViewModel()
    // ViewModel에서 더미 데이터를 로드
    LaunchedEffect(Unit) {
        mockViewModel.loadAlbum(1)
        mockViewModel.loadSong(1)
    }

    // Get list of liked songs from the view model
    val likedSongs = mockViewModel.getLikedSongs().observeAsState(emptyList()).value
    // Track the selected state for each item
    val selectedSongs = remember { mutableStateOf(likedSongs.map { it.id to false }.toMap()) }
    // Handle select all functionality
    val onSelectAllClick: () -> Unit = {
        val allSelected = likedSongs.all { selectedSongs.value[it.id] == true }
        // Toggle all items based on current state
        selectedSongs.value = likedSongs.associate { it.id to !allSelected }
    }

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
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier.padding(end = 150.dp)
                ) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 },
                        text = { Text("저장한 곡") }
                    )
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 },
                        text = {
                            Text(
                                "음악 파일",
                                color = Color.Black
                            )
                        }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onSelectAllClick) {
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
                MiniPlayer(viewModel = mockViewModel, progress = 0f)
            }
        }
    ) { innerPadding ->
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
            itemsIndexed(likedSongs) { index, song ->
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
                        onClick = { mockViewModel.toggleLikeStatus(song) }
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
}


@Preview
@Composable
fun LockerFragmentPreview() {
    LockerFragment()
}