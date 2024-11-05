package umc.study.umc_7th.locker

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable


import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape


import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow


import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
//import com.google.accompanist.pager.*

import kotlinx.coroutines.launch
import umc.study.umc_7th.Content
import umc.study.umc_7th.R


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
            Row(
                modifier = Modifier.align(Alignment.CenterEnd)
            ){
                Text(text= "로그인", color = Color.Blue)
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun LockerTab(){
    val pages= listOf("저장한 곡", "음악파일")
    val pagerState= rememberPagerState { pages.size }
    val coroutineScope = rememberCoroutineScope()
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
                selectAllButtonClick = {},
                playAllButtonClick = {},
                contentList = listOf(
                    Content(
                        title = "Butter",
                        author = "BTS",
                        image = R.drawable.img_album_exp,
                        length = 200)
                    , Content(
                        title = "Next Level",
                        author = "aespa",
                        image = R.drawable.img_album_exp3,
                        length = 212),
                    Content(
                        title = "해야",
                        author = "IVE",
                        image = R.drawable.img_album_heya,
                        length = 199
                        ),
                    Content(
                        title = "LILAC",
                        author = "IU",
                        image = R.drawable.img_album_exp2,
                        length = 21),
                    Content(
                        title = "Dionysious",
                        author = "BTS",
                        image = R.drawable.img_album_exp4,
                        length= 229),
                    Content(
                        title = "Drama",
                        author ="aespa",
                        image = R.drawable.img_album_drama,
                        length = 231
                    ),
                    Content(
                        title = "Supernova",
                        author ="aespa",
                        image = R.drawable.img_album_supernova,
                        length = 192
                    ),
                    Content(
                        title = "Love Wins All",
                        author = "IU",
                        image = R.drawable.img_album_lovewinsall,
                        length = 262)
                ),

            )
            1 -> LockerMusicFile()
        }
    }
    }


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
fun LockerFragment(navController: NavController){

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            LockerFragment1()
            LockerTab()
        }



}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun PreviewLockerFragment(){
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LockerFragment1()
            LockerTab()
        }
    }


}