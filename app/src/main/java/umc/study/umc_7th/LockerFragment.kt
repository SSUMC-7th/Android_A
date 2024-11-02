package umc.study.umc_7th

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.ui.theme.Purple40

data class Music(
    val albumImage: Int,
    val title: String,
    val artist: String
)


@Composable
fun LockerFragment() {

    var selectedTabIndex by remember { mutableStateOf(0) }

    // 더미 데이터
    val musicList = listOf(
        Music(R.drawable.img_album_exp2, "라일락", "아이유 (IU)"),
        Music(R.drawable.img_album_drama, "Drama", "에스파"),
        Music(R.drawable.img_album_exp, "Butter", "BTS"),
        Music(R.drawable.img_album_lovewinsall, "Love wins all", "아이유 (IU)"),
        Music(R.drawable.img_album_exp3, "Next Level", "에스파"),
        Music(R.drawable.img_album_exp4, "작은 것들을 위한 시 (Boy With Luv) (Feat. Halsey)", "BTS"),
        Music(R.drawable.img_album_exp5, "BAAM", "모모랜드(MOMOLAND)"),
        Music(R.drawable.img_album_exp6, "Weekend", "태연(TAEYEON)"),
        Music(R.drawable.img_album_heya, "해야 (HEYA)", "IVE"),
        Music(R.drawable.img_album_supernova, "Supernova", " 에스파"),
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.btn_playlist_select_off
                        ),
                        contentDescription = "selectAll"
                    )
                    Text(
                        text = "전체선택",
                        style = TextStyle(fontSize = 18.sp)
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        painter = painterResource(
                            id = R.drawable.icon_browse_arrow_right
                        ),
                        contentDescription = "listenAll"
                    )
                    Text(
                        text = "전체듣기",
                        style = TextStyle(fontSize = 18.sp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "편집",
                        style = TextStyle(fontSize = 18.sp),
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }
            }
        },
        bottomBar = {
            Column {
                MiniPlayer(viewModel = MusicViewModel(), progress = 0f)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(musicList) { Music ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(painter = painterResource(
                        id = Music.albumImage),
                        contentDescription = "albumImage",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(start = 5.dp)
                    )
                    Column(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Text(
                            text = Music.title,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier
                                .padding(bottom = 4.dp)
                                .width(200.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = Music.artist,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier.width(200.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
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



@Preview
@Composable
fun LockerFragmentPreview() {
    LockerFragment()
}