package umc.study.umc_7th

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import umc.study.umc_7th.album.AlbumMusicList
import umc.study.umc_7th.content.Album
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun TabLayout(
    album : Album,
) {
    val tabs = listOf("수록곡", "상세정보", "영상")
    val pagerState = rememberPagerState{ tabs.size }
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .padding(horizontal = tabPositions[pagerState.currentPage].width / 4),
                    color = Color.Blue,
                    height = 4.dp,

                    )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title, fontSize = 16.sp,
                        color = if(pagerState.currentPage ==index) Color.Blue
                        else Color.Black)
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> AlbumMusicList(
                    album = album,
                    playButtonClick = { /*TODO*/ },
                    playAllButtonClick = { /*TODO*/ },
                    selectAllButtonClick = { /*TODO*/ },
                    mixButtonClick = {},
                    moreInfoButtonClick = {})
                1 -> Text("상세정보 내용", modifier = Modifier.padding(16.dp))
                2 -> Text("영상 내용", modifier = Modifier.padding(16.dp))
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewTabLayout(){
    TabLayout(album = Album(
        albumTitle = "IU 5th Album 'LILAC'",
        date = LocalDate.parse("2023-03-27"),
        author = "IU(아이유)",
        albumImage =  R.drawable.img_album_exp2,
        trackList = listOf("LILAC", "Coin", "Flu", "Troll", "Lovesick"),
        titleTrackList = listOf("LILAC", "Flu")
    )
    )
}