package umc.study.umc_7th.song

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayProgressControlPanel(
    length: Int,
    playingPoint: Int,
    isPlaying: Boolean,
    isRepeating: Boolean,
    isShuffling: Boolean,
    isLiked: Boolean,
    isBlocked: Boolean,
    onLikeButtonClicked : (Boolean) -> Unit,
    onBlockButtonClicked : (Boolean) -> Unit,
    onRepeatButtonClicked : (Boolean) -> Unit,
    onShuffleButtonClicked : (Boolean) -> Unit,
    onPreviousButtonClicked : () -> Unit,
    onNextButtonClicked : () -> Unit,
    onPlayButtonClicked : (Boolean) -> Unit,
    onPlayingPointChanged: (Int) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        // 좋아요와 싫어요
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                32.dp,
                alignment = Alignment.CenterHorizontally
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { onLikeButtonClicked(!isLiked) }) {
                Image(
                    painter = painterResource(
                        id = if (isLiked) R.drawable.ic_my_like_on
                        else R.drawable.ic_my_like_off
                    ),
                    contentDescription = null,
                )
            }
            IconButton(onClick = { onBlockButtonClicked(!isBlocked) }) {
                Image(
                    painter = painterResource(
                        id = if (isBlocked) R.drawable.btn_player_unlike_on
                        else R.drawable.btn_player_unlike_off
                    ),
                    contentDescription = null,
                )
            }
        }
        // 재생 위치 표시 및 조정
        Column {
            Slider(
                value = playingPoint.toFloat(),
                onValueChange = { onPlayingPointChanged(it.toInt()) },
                valueRange = 0f..length.toFloat(),
                thumb = {},
                colors = SliderDefaults.colors(
                    activeTrackColor = Color.Blue,
                ),
                modifier = Modifier.height(24.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "${"%02d".format(playingPoint / 60)}:${"%02d".format(playingPoint % 60)}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Blue,
                    )
                )
                Text(
                    text = "${"%02d".format(length / 60)}:${"%02d".format(length % 60)}",
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black.copy(alpha = 0.5f)
                    )
                )
            }
        }
        // 버튼 바
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { onRepeatButtonClicked(!isRepeating) },
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.nugu_btn_repeat_inactive),
                    contentDescription = null,
                    tint = if (isRepeating) Color.Blue else Color.Black.copy(alpha = 0.5f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = onPreviousButtonClicked,
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.nugu_btn_skip_previous_32),
                        contentDescription = null,
                    )
                }
                IconButton(
                    onClick = { onPlayButtonClicked(!isPlaying) },
                    modifier = Modifier.size(40.dp),
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isPlaying) R.drawable.nugu_btn_pause_32
                            else R.drawable.nugu_btn_play_32
                        ),
                        contentDescription = null,
                    )
                }
                IconButton(
                    onClick = onNextButtonClicked,
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.nugu_btn_skip_next_32),
                        contentDescription = null,
                    )
                }
            }
            IconButton(
                onClick = { onShuffleButtonClicked(!isShuffling) },
                modifier = Modifier.size(48.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.nugu_btn_random_inactive),
                    contentDescription = null,
                    tint = if (isShuffling) Color.Blue else Color.Black.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlayProgressControlPanel() {
    PlayProgressControlPanel(
        length = 181,
        playingPoint = 125,
        isPlaying = true,
        isRepeating = true,
        isShuffling = false,
        isLiked = true,
        isBlocked = false,
        onLikeButtonClicked = {},
        onBlockButtonClicked = {},
        onRepeatButtonClicked = {},
        onShuffleButtonClicked = {},
        onPreviousButtonClicked = {},
        onNextButtonClicked = {},
        onPlayButtonClicked = {},
        onPlayingPointChanged = {},
    )
}