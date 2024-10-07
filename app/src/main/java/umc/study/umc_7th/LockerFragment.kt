package umc.study.umc_7th

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Scaffold


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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
//import com.google.accompanist.pager.*

import kotlinx.coroutines.launch


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
        TabRow(selectedTabIndex = pagerState.currentPage,
            containerColor = Color.White,
            modifier = Modifier.fillMaxWidth(),

            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .padding(horizontal = tabPositions[pagerState.currentPage].width / 3),
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
            0 -> LockerMusic()
            1 -> LockerMusicFile()
        }
    }
    }


}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LockerMusic(){
    Scaffold { innerPadding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            Text(text = "저장한 곡")
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
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            LockerFragment1()
            LockerTab()
        }
    }


}