package umc.study.umc_7th

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable


import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState


import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text


import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
//import com.google.accompanist.pager.*

import kotlinx.coroutines.launch


import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun TabLayout() {
    val tabs = listOf("수록곡", "상세정보", "영상")
    val pagerState = rememberPagerState{ tabs.size }
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
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
                0 -> AlbumMusic(
                    album = Album(
                    albumTitle = "IU 5th Album 'LILAC'",
                    date = LocalDate.parse("2023-03-27"),
                    author = "IU(아이유)",
                    albumImage = ImageBitmap.imageResource(id =R.drawable.img_album_exp2 ) ,
                    trackList = listOf("LILAC", "Coin", "Flu", "Troll", "Lovesick"),
                    titleTrackList = listOf("LILAC", "Flu")
                ),
                    playButtonClick = { /*TODO*/ },
                    playAllButtonClick = { /*TODO*/ },
                    selectAllButtonClick = { /*TODO*/ }){}
                1 -> Text("상세정보 내용", modifier = Modifier.padding(16.dp))
                2 -> Text("영상 내용", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun AlbumFragment1(
    album : Album,
    date : LocalDate ,
    albumFgtoMain: () -> Unit,
    likeButtonClick : () -> Unit,
    playerMoreButtonClick: () -> Unit,
    playAlbumButtonClick : () -> Unit,

){
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(10.dp)

    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ){
            IconButton(onClick = albumFgtoMain) {
                Icon(bitmap =ImageBitmap.imageResource(id = R.drawable.btn_arrow_more),
                    contentDescription =null,
                    modifier = Modifier.size(20.dp))
            }
            Row (
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ){
                var like by remember { mutableStateOf(true) }
                IconButton(onClick = likeButtonClick) {
                    Icon(bitmap = if(like == true) ImageBitmap.imageResource(id = R.drawable.ic_my_like_on)
                    else ImageBitmap.imageResource(id = R.drawable.ic_my_like_off),
                        contentDescription = null,
                        modifier = Modifier.clickable { like = !like })
                }

                Icon(bitmap =ImageBitmap.imageResource(id = R.drawable.btn_player_more),
                    contentDescription =null,
                    modifier = Modifier.clickable { playerMoreButtonClick() })
            }

        }
        Column( verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = album.albumTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(text = album.author,
                style = TextStyle(
                    color = Color.Black.copy(alpha = 0.5f)
                ),
                fontSize = 12.sp
            )
            val dateString = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            val genre = "댄스 팝"
            val regular = "정규"
            Text(text ="$dateString | $regular | $genre",)
        }
            Row(
                modifier = Modifier.padding(start = 70.dp)
            ){
                Box{
                    Image(bitmap = album.albumImage,
                        contentDescription =null ,
                        modifier = Modifier
                            .size(210.dp)
                            .align(Alignment.Center))
                    IconButton(onClick = playAlbumButtonClick,
                        modifier = Modifier.align(Alignment.BottomEnd)) {
                        Icon(bitmap= ImageBitmap.imageResource(id = R.drawable.widget_black_play),
                            contentDescription =null )
                    }
                }
                Image(painter = painterResource(id = R.drawable.img_album_lp),
                    contentDescription =null )

            }
        }
    }



enum class SongFragmentSimpleNavigation(
    val mean : String,
){
    trackList("수록곡"),
    moreInfo("상세정보"),
    video("영상")

}
@Composable
fun AlbumFragmentNavigationBar(){
    var selectedButton by remember {
        mutableStateOf(SongFragmentSimpleNavigation.trackList)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SongFragmentSimpleNavigation.entries.forEach { selection ->
            Text(text = selection.mean,
                color = if (selectedButton == selection)
                Color.Blue
                else
                Color.Black.copy(alpha=0.5f)
            )
                

        }
    }
}
//
//@RequiresApi(Build.VERSION_CODES.P)
//@OptIn(ExperimentalPagerApi::class)
//@Composable
//fun AlbumTabRow(){
//    val pages= listOf("수록곡", "상세정보", "영상")
//    val pagerState= rememberPagerState { pages.size }
//    val coroutineScope = rememberCoroutineScope()
//    TabRow(selectedTabIndex = pagerState.currentPage,
//        containerColor = Color.Blue,
//        contentColor = Color.White,
//        modifier =Modifier.fillMaxWidth(),
//        indicator = { tabPositions ->
//            TabRowDefaults.Indicator(
//                modifier = Modifier
//                    .padding(horizontal = tabPositions[pagerState.currentPage].width / 4)
//                    .fillMaxWidth()
//                    .height(4.dp),
//                color = Color.White
//            )
//        }
//        ) {
//        pages.forEachIndexed{ index, title ->
//            Tab(
//                selected = pagerState.currentPage==index,
//                onClick = {
//                    coroutineScope.launch {
//                        pagerState.animateScrollToPage(index)
//                    }
//                },
//                content = {
//                    Text(text = title,
//                        color = if(pagerState.currentPage ==index) Color.White
//                    else Color.Gray,
//                    modifier = Modifier.padding(10.dp))
//                }
//            )
//
//        }
//
//    }
//    HorizontalPager(state = pagerState,
//    modifier = Modifier.fillMaxWidth()
//    ) { page ->
//        when (page){
//            0 -> AlbumMusic(
//                album = Album(
//                    albumTitle = "IU 5th Album 'LILAC'",
//                    date = LocalDate.parse("2023-03-27"),
//                    author = "IU(아이유)",
//                    albumImage = ImageBitmap.imageResource(id =R.drawable.img_album_exp2 ) ,
//                    trackList = listOf("LILAC", "Coin", "Flu", "Troll", "Lovesick"),
//                    titleTrackList = listOf("LILAC", "Flu")
//                ),
//                playButtonClick = { /*TODO*/ },
//                playAllButtonClick = { /*TODO*/ },
//                selectAllButtonClick = { /*TODO*/ }) {}
//            1-> AlbumInfo()
//            2-> AlbumVideo()
//        }
//
//    }
//}


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AlbumMusic(
    album : Album,
    playButtonClick : () -> Unit,
    playAllButtonClick : () -> Unit,
    selectAllButtonClick : () -> Unit,
    moreInfoButtonClick :() -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxHeight()
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "내 취향 MIX", fontSize = 15.sp)

                    var checked by remember { mutableStateOf(false) }
                    Icon(painter = painterResource(
                        id = if (checked == false) R.drawable.btn_toggle_off
                        else R.drawable.btn_toggle_on
                    ), contentDescription = null,
                        modifier = Modifier
                            .clickable { checked = !checked }
                            .size(20.dp))
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    var selected by remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier
                            .clickable { selectAllButtonClick() }
                            .align(Alignment.CenterStart)

                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (selected == false) R.drawable.btn_playlist_select_off
                                else R.drawable.btn_playlist_select_on
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "전체 선택",
                            fontSize = 12.sp,
                            color = if (selected == false) Color.Black else Color.Blue
                        )
                    }
                    Row(
                        modifier = Modifier
                            .clickable { playAllButtonClick() }
                            .align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_browse_arrow_right),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "전체 듣기", color = Color.Black,
                            fontSize = 12.sp
                        )
                    }


                }
            }

        }

        itemsIndexed(album.trackList) { index, track ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "${index + 1}", Modifier.padding(horizontal = 4.dp),
                    fontSize = 12.sp, fontWeight = FontWeight.Bold
                )
                Column {
                    Text(text = track)
                    Text(
                        text = album.author,
                        color = Color.Black.copy(0.5f),
                        fontSize = 12.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(painter = painterResource(id = R.drawable.btn_player_play),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { playButtonClick() })
                    Icon(painter = painterResource(id = R.drawable.btn_player_more),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { moreInfoButtonClick() })
                }
            }


        }


    }
}


@Composable
fun AlbumInfo(){
    Text(text ="앨범 정보")
}

@Composable
fun AlbumVideo(){
    Text(text = "앨범 영상")
}


@SuppressLint("NewApi")
@Composable
fun albumFragment(
    navController: NavController,
    albumTitle: String,
    author: String,
    date: LocalDate,
    trackList: List<String>,
    titleTrackList: List<String>
){
    Column {
        AlbumFragment1(
            album = Album(
                albumTitle = albumTitle,
                date = date,
                author = author,
                albumImage = ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
                trackList = trackList,
                titleTrackList = titleTrackList
            ),
            date = date,
            albumFgtoMain = { navController.navigate("homeFragment") },
            likeButtonClick = { /*TODO*/ },
            playerMoreButtonClick = { /*TODO*/ }) {

        }
        TabLayout()
    }

}