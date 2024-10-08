package umc.study.umc_7th

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.ui.theme.Purple40

@Composable
fun TopButtonsView() {

    val context = LocalContext.current

    Row() {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.btn_player_setting),
                contentDescription = "setting"
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.btn_player_eq_off),
                contentDescription = "eq"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column() {
            IconButton(onClick = {
                val intent = Intent(context, MainActivity::class.java).apply {
                putExtra("songTitle", "라일락")
                putExtra("songAuthor", "아이유 (IU)")
            }
                context.startActivity(intent)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.nugu_btn_down),
                    contentDescription = "backPress"
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_player_more),
                    contentDescription = "more"
                )
            }
        }
    }
}

@Composable
fun Album() {

    var isColorful_like by remember { mutableStateOf(false) }
    var isColorful_unlike by remember { mutableStateOf(false) }

    var currentTime by remember { mutableStateOf(0f) } // 현재 시간 상태
    val totalTime = 60f // 전체 시간

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "라일락",
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.padding(start = 45.dp),
        ) {
            Text(
                text = "아이유 (IU)",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 12.dp)
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_arrow_more),
                    contentDescription = "more"
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.img_album_exp2),
            contentDescription = "albumImage",
            modifier = Modifier
                .size(200.dp)
                .padding(15.dp)
                .clip(RoundedCornerShape(5.dp))
        )

        Text(
            text = "나라는 꽃가루에\n 눈이 따끔해 아야",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { isColorful_like = !isColorful_like }
            ) {
                Icon(
                    painter = painterResource(id = if (isColorful_like) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off),
                    contentDescription = "like",
                    tint = if (isColorful_like) Color.Blue else Color.Black
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            IconButton(
                onClick = { isColorful_unlike = !isColorful_unlike }
            ) {
                Icon(
                    painter = painterResource(id = if (isColorful_unlike) R.drawable.btn_player_unlike_on else R.drawable.btn_player_unlike_off),
                    contentDescription = "unlike",
                    modifier = Modifier.size(40.dp),
                    tint = if (isColorful_unlike) Color.Blue else Color.Black
                )
            }
        }

        MusicProgressBar(currentTime = currentTime, totalTime = totalTime)
        
        var isPlay by remember { mutableStateOf(true) }
        
        Row() {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.nugu_btn_repeat_inactive),
                    contentDescription = "repeat"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(end = 15.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.nugu_btn_skip_previous_32),
                    contentDescription = "previous"
                )
            }
            IconButton(onClick = { isPlay = !isPlay }) {
                Icon(
                    painter = painterResource(id = if (isPlay) R.drawable.nugu_btn_play_32 else R.drawable.btn_miniplay_pause),
                    contentDescription = "play"
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(start = 15.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.nugu_btn_skip_next_32),
                    contentDescription = "next"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.nugu_btn_random_inactive),
                    contentDescription = "random"
                )
            }
        }
    }
}

@Composable
fun BottomBar() {

    Row {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.btn_actionbar_instagram),
                contentDescription = "insta",
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.btn_player_related),
                contentDescription = "related"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.btn_player_go_list),
                contentDescription = "list"
            )
        }
    }
}

@Composable
fun MusicProgressBar(currentTime: Float, totalTime: Float) {
    val progress = currentTime / totalTime

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // 여백 추가
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 프로그레스 바
        Slider(
            value = currentTime,
            onValueChange = {},
            valueRange = 0f..totalTime,
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
            )
        )

        // 현재 시간과 전체 시간을 표시하는 텍스트
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = formatTime(currentTime),
                fontSize = 14.sp,
                color = Purple40
            )
            Text(
                text = formatTime(totalTime),
                fontSize = 14.sp
            )
        }
    }
}

fun formatTime(seconds: Float): String {
    val totalSeconds = seconds.toInt()
    val minutes = totalSeconds / 60
    val secondsRemainder = totalSeconds % 60
    return String.format("%02d:%02d", minutes, secondsRemainder)
}

@Preview
@Composable
fun PreviewTopButtonsView() {
    TopButtonsView()
}

@Preview
@Composable
fun PreviewAlbum() {
    Album()
}

@Preview
@Composable
fun PreviewBottomBar() {
    BottomBar()
}