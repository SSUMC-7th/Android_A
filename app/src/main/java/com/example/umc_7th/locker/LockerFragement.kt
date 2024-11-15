// 파일 경로: com.example.umc_7th.locker

package com.example.umc_7th.locker

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
import kotlinx.coroutines.launch
import com.example.umc_7th.etc.Content
import com.example.umc_7th.R

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
                Text(text = "보관함", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 24.sp)
            }
            Row(
                modifier = Modifier.align(Alignment.CenterEnd)
            ){
                Text(text = "로그인", color = Color.Blue)
            }
        }
    }
}

@Composable
fun LockerTab(){
    val pages= listOf("저장한 곡", "음악파일")
    val pagerState= rememberPagerState { pages.size }
    val coroutineScope = rememberCoroutineScope()
    Column {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
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
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title, fontSize = 16.sp, color = if (pagerState.currentPage == index) Color.Blue else Color.Black) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    }
                )
            }
        }
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
            when (page) {
                0 -> LockerMusic(
                    selectAllButtonClick = {},
                    playAllButtonClick = {},
                    contentList = listOf(
                        Content("Butter", "BTS", R.drawable.img_album_exp, 200),
                        Content("Next Level", "aespa", R.drawable.img_album_exp3, 210),
                    )
                )
                1 -> Text("음악 파일") // Placeholder for music files page
            }
        }
    }
}

@Composable
fun LockerMusic(
    selectAllButtonClick: () -> Unit,
    playAllButtonClick: () -> Unit,
    contentList: List<Content>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "저장한 곡",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
            Row {
                Text(
                    text = "전체 선택",
                    color = Color.Blue,
                    modifier = Modifier.clickable { selectAllButtonClick() }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "모두 재생",
                    color = Color.Blue,
                    modifier = Modifier.clickable { playAllButtonClick() }
                )
            }
        }

        LazyColumn {
            items(contentList) { content ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    content.image?.let {
                        Image(
                            painter = painterResource(id = it),
                            contentDescription = null,
                            modifier = Modifier.size(64.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = content.title, fontWeight = FontWeight.Bold)
                        Text(text = content.author, color = Color.Gray)
                    }
                }
            }
        }
    }
}
