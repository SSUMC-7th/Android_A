package umc.study.umc_7th.ui.screen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.livedata.observeAsState
import umc.study.umc_7th.MainActivity
import umc.study.umc_7th.R
import umc.study.umc_7th.ui.composables.MiniPlayer
import umc.study.umc_7th.ui.composables.TabItem
import umc.study.umc_7th.ui.composables.TabLayout
import umc.study.umc_7th.ui.viewmodel.MockMusicViewModel
import umc.study.umc_7th.ui.viewmodel.MusicViewModel

@Composable
fun AlbumFragment(viewModel: MusicViewModel = MockMusicViewModel()) {

    val context = LocalContext.current
    var isColorful_like by remember { mutableStateOf(false) }

    val album by viewModel.album.observeAsState(initial = null)
    val songList by viewModel.songList.observeAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.loadAlbum(1)
        viewModel.loadSong(1)
    }

    val albumTitle = album?.firstOrNull()?.title ?: "No Album Title"
    val albumSinger = album?.firstOrNull()?.singer ?: "No Album Singer"

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column {
                MiniPlayer(
                    viewModel = viewModel,
                    progress = 0f
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                IconButton(onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_arrow_black),
                        contentDescription = "back"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { isColorful_like = !isColorful_like }) {
                    Icon(
                        painter = painterResource(id = if (isColorful_like) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off),
                        contentDescription = "like",
                        tint = if (isColorful_like) Color.Blue else Color.Black
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_player_more),
                        contentDescription = "more"
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = albumTitle,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = albumSinger,
                    style = MaterialTheme.typography.bodyMedium
                    )
                Text(
                    text = R.string.album_info.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Row {
                    Box(
                        modifier = Modifier.size(250.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_album_exp2),
                            contentDescription = "Album",
                            modifier = Modifier
                                .height(230.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.btn_miniplayer_play),
                            contentDescription = "play",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.img_album_lp),
                        contentDescription = null,
                        modifier = Modifier.height(270.dp)
                    )
                }
            }
            TabLayout(
                tabs = listOf(
                TabItem(
                    label = "수록곡",
                    page = {},
                ),
                TabItem(
                    label = "상세정보",
                    page = {},
                ),
                TabItem(
                    label = "영상",
                    page = {},
                ),
            ))
            var isMixed by remember { mutableStateOf(false) }
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
                    onClick = { isMixed = !isMixed },
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
                ) {
                    Image(
                        painter = painterResource(
                            id = R.drawable.btn_playlist_select_off
                        ),
                        contentScale = ContentScale.Fit,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "전체선택",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.Unspecified
                        ),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(percent = 50))
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(songList) { index, song ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // 왼쪽 번호 및 텍스트 영역
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "0$index",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 20.dp, end = 8.dp)
                            )
                            Column {
                                Row {
                                    // 제목에 'Title' 표시
                                    // 일단 라일락의 경우만 구현
                                    // db에 이 곡이 앨범의 타이틀인지 아닌지 구현해야함
                                    if (song.title == "LILAC") {
                                        Text(
                                            text = "TITLE",
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier
                                                .padding(end = 4.dp)
                                                .background(Color.Blue, shape = RoundedCornerShape(10.dp))
                                                .padding(4.dp)
                                        )
                                    }
                                    Text(
                                        text = song.title,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                }
                                Text(
                                    text = song.singer,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            // 오른쪽 아이콘들
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

@Preview(apiLevel = 34)
@Composable
fun AlbumFragmentPreview() {

    val mockViewModel = MockMusicViewModel()
    LaunchedEffect(Unit) {
        mockViewModel.insertDummyAlbums() // Ensure albums are inserted
    }
    AlbumFragment(viewModel = mockViewModel)
}