package umc.study.umc_7th

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MiniPlayer (songTitle: String?, songAuthor: String?, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val title: String = if (songTitle != null) {
        songTitle
    } else {
        "제목"
    }
    val singer: String = if (songAuthor != null) {
        songAuthor
    } else {
        "가수"
    }
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        containerColor = Color.LightGray
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column()
            {
                // 곡 제목
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 20.dp, top = 3.dp)
                )
                // 아티스트
                Text(
                    text = singer,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            // 재생 버튼
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = { /* 이전 곡 로직 */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_miniplayer_previous),
                        contentDescription = "Previous"
                    )
                }
                IconButton(onClick = { /* 재생/일시 정지 로직 */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_miniplayer_play),
                        contentDescription = "Play/Pause"
                    )
                }
                IconButton(onClick = { /* 다음 곡 로직 */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_miniplayer_next),
                        contentDescription = "Next"
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(onClick = {
                      val intent = Intent(context, SongActivity::class.java)
                      context.startActivity(intent)
                }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_miniplayer_go_list),
                        contentDescription = "Info"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMiniPlayer() {
    MiniPlayer("라일락", "아이유(IU)")
}