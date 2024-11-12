package umc.study.umc_7th.ui.composables

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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import umc.study.umc_7th.R
import umc.study.umc_7th.SongActivity
import umc.study.umc_7th.ui.viewmodel.MockMusicViewModel
import umc.study.umc_7th.ui.viewmodel.MusicViewModel

@Composable
fun MiniPlayer (
    viewModel: MusicViewModel,
    progress: Float,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val isPlaying by viewModel.isPlaying.collectAsState()

    val currentSong = viewModel.getCurrentSong()

    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        containerColor = Color.LightGray
    ) {
        Column() {
            LinearProgressIndicator(
                progress = { progress.coerceIn(0f, 1f) },
                modifier = Modifier.fillMaxWidth(),
            )

            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column()
                {
                    currentSong?.let {
                        // 곡 제목
                        Text(
                            text = it.title,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 20.dp, top = 3.dp)
                        )
                        // 아티스트
                        Text(
                            text = it.singer,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                // 재생 버튼
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = { viewModel.previousSong() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.btn_miniplayer_previous),
                            contentDescription = "Previous"
                        )
                    }
                    IconButton(onClick = { viewModel.togglePlayPause() }) {
                        Icon(
                            painter = painterResource(if (isPlaying) R.drawable.btn_miniplay_pause else R.drawable.btn_miniplayer_play),
                            contentDescription = "Play/Pause"
                        )
                    }
                    IconButton(onClick = { viewModel.nextSong() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.btn_miniplayer_next),
                            contentDescription = "Next"
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    IconButton(onClick = {
                        currentSong?.let { song ->
                            val intent = Intent(context, SongActivity::class.java).apply {
                                putExtra("song_id", 0)
                            }
                            context.startActivity(intent)
                        }
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
}

@Preview(showBackground = true)
@Composable
fun PreviewMiniPlayer() {
    val mockViewModel = MockMusicViewModel()
    MiniPlayer(
        viewModel = mockViewModel,
        progress = 0.5f,
        )
}