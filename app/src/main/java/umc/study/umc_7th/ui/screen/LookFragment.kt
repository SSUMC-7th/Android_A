package umc.study.umc_7th.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.R

@Composable
fun LookFragment() {

    val tabTitles = listOf("차트", "영상", "장르", "상황", "분위기", "오디오")
    val selectedTabIndex = remember { mutableStateOf(0) }

    Column {
        Text(
            text = "둘러보기",
            style = MaterialTheme.typography.headlineLarge
        )
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex.value,
            edgePadding = 16.dp,
            divider = {},
            indicator = {},
            containerColor = Color.Transparent
        ) {
            tabTitles.forEachIndexed { index, title ->
                RoundTab(
                    title = title,
                    selected = selectedTabIndex.value == index,
                    onClick = { selectedTabIndex.value = index }
                )
            }
        }
        // 탭에 따른 화면 변경
        when (selectedTabIndex.value) {
            0 -> ChartScreen()
            /*1 -> VideoScreen()
            2 -> GenreScreen()
            3 -> SituationScreen()
            4 -> MoodScreen()
            5 -> AudioScreen()*/
        }
    }
}

@Composable
fun RoundTab(title: String, selected: Boolean, onClick: () -> Unit) {

    val backgroundColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable(onClick = onClick)
            .background(
                color = backgroundColor,
                shape = CircleShape // 동그란 모양
            )
            .padding(horizontal = 16.dp, vertical = 8.dp) // 내부 패딩 조절
    ) {
        Text(
            text = title,
            color = contentColor,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ChartScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 상단 텍스트와 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = "차트",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Image(
                painter = painterResource(id = R.drawable.btn_arrow_more),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        // 하단 사각형 콘텐츠
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            // 콘텐츠 예시
            Column {
                Row {
                    Column {
                        Text(
                            text = "FLO 차트 19시 기준",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = "최근 24시간 집계, FLO 최고 인기곡 차트!",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { /* 전체듣기 버튼 동작 */ },
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .width(500.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_browse_arrow_right),
                            contentDescription = "PlayAllButton",
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            "전체듣기",
                            fontSize = 10.sp
                        )
                    }
                }
                LazyColumn {
                    items(10) { index ->
                        Text("Item $index", modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LookFragmentPreview() {
    LookFragment()
}