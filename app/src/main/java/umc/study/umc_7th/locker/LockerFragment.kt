package umc.study.umc_7th.locker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable


import androidx.compose.foundation.layout.*

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState


import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow


import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.google.accompanist.pager.*

import kotlinx.coroutines.launch
import umc.study.umc_7th.user.LoginActivity
import umc.study.umc_7th.SongViewModel


@Composable
fun LockerFragment1(){
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(16.dp)
            .background(color = Color.White)

    ){
        Box(
            modifier = Modifier.fillMaxWidth()
        ){
            Row(
              modifier = Modifier.align(Alignment.CenterStart)
            ){
                Text( text = "보관함", fontWeight = FontWeight.Bold, color = Color.Black,
                    fontSize = 24.sp)
            }
            val context = LocalContext.current
            Row(
                modifier = Modifier.align(Alignment.CenterEnd)
                    .clickable { val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)}
            ){
                Text(text= "로그인", color = Color.Blue)
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun LockerTab(viewModel: SongViewModel,
              showBottomBar: () -> Unit,
              hideBottomBar: () -> Unit){
    val pages= listOf("저장한 곡", "음악파일","앨범 like") // 이제 8장 ====
    val pagerState= rememberPagerState { pages.size }
    val coroutineScope = rememberCoroutineScope()
    val likeSongs by viewModel.likedSongs.observeAsState(emptyList())

    LaunchedEffect(likeSongs) {
        viewModel.loadLikedSongs()
    }
    Column {
        ScrollableTabRow(selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White,
            edgePadding = 0.dp,


            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .padding(horizontal = tabPositions[pagerState.currentPage].width / 4),
                    color = Color.Blue,
                    height = 2.dp
                )
            }
        ) {
            pages.forEachIndexed{ index, title ->
                Tab(
                    text = { Text(text= title, fontSize= 16.sp,
                        color = if(pagerState.currentPage ==index) Color.Blue
                        else Color.Black,)},
                    selected = pagerState.currentPage==index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },

                )

            }

        }
        HorizontalPager(state = pagerState,
        modifier = Modifier.fillMaxWidth()
        ) { page ->
        when (page){
            0 -> LockerMusic(
                showBottomBar =showBottomBar,
                hideBottomBar = hideBottomBar,
                selectAllButtonClick = {},
                playAllButtonClick = {},
                contentList = likeSongs,
                viewModel= viewModel

            )
            1 -> LockerMusicFile()
            2 -> LockerAlbumLike()
        }
    }
    }


}

@Composable
fun LockerAlbumLike(){

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LockerMusicFile() {
    Scaffold { innerPadding->
        Column (modifier= Modifier
            .fillMaxSize()
            .padding(innerPadding)){
            Text(text = "음악 파일")
        }
    }
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LockerFragment(viewModel : SongViewModel,
                   showBottomBar: () -> Unit,
                   hideBottomBar: () -> Unit){

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LockerFragment1()
            LockerTab(viewModel = viewModel,
                showBottomBar = showBottomBar,
                hideBottomBar = hideBottomBar)
        }



}
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Preview(showBackground = true)
//@Composable
//fun PreviewLockerFragment(){
//    Scaffold { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//        ) {
//            LockerFragment1()
//            LockerTab()
//        }
//    }
//
//
//}