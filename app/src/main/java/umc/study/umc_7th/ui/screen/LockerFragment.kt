package umc.study.umc_7th.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import umc.study.umc_7th.ui.composables.MiniPlayer
import umc.study.umc_7th.ui.theme.Purple40
import umc.study.umc_7th.ui.viewmodel.MockMusicViewModel

@Composable
fun LockerFragment() {

    var selectedTabIndex by remember { mutableStateOf(0) }

    val mockViewModel = MockMusicViewModel()
    // ViewModel에서 더미 데이터를 로드
    mockViewModel.loadAlbum(1)

    Scaffold(
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
            items(1) { item ->
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
                }
                Column() {
                    Spacer(modifier = Modifier.height(300.dp))
                    Text(
                        text = "저장한 곡이 없습니다.",
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(150.dp))
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