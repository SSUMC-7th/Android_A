package umc.study.umc_7th

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Slider


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun MiniPlayer(
    viewModel: SongViewModel,

    beforeSongPlayButtonClick: () -> Unit,
    playSongButtonClick: () -> Unit,
    nextSongPlayButtonClick: () -> Unit,
    musicQueueClick: () -> Unit,
    toSongActivity: (Content) -> Unit,
){
    val currentSong by viewModel.currentSong.observeAsState()

    currentSong?.let{ content ->
        Column {
            val currentPosition by viewModel.currentPosition.observeAsState(0f)
            val duration by viewModel.duration.observeAsState(1f)

            val progress = currentPosition / duration

            Slider(
                value = progress,
                onValueChange = { newProgress ->
                    viewModel.updatePosition(newProgress * duration)
                },
                modifier = Modifier
                    .fillMaxWidth().height(3.dp),
                thumb = {},
            )
            Box(modifier = Modifier.clickable {toSongActivity(content) }
            )
            {
                Row(
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(120.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(
                        verticalArrangement = Arrangement.spacedBy(1.dp)
                    ){
                        Text(text = content.title,
                            fontSize = 18.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis)
                        Text(
                            text = content.author,
                            fontSize = 15.sp,
                            style = TextStyle(
                                color = TextStyle.Default.color.copy(alpha = 0.5f)
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ){  val played by viewModel.played.observeAsState(true)
                        Icon(painter = painterResource(id = R.drawable.btn_miniplayer_previous),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable { beforeSongPlayButtonClick() })
                        Icon(painter = painterResource(id = if(played) R.drawable.btn_miniplay_pause
                        else R.drawable.btn_miniplayer_play),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    viewModel.togglePlayed()
                                    playSongButtonClick()
                                })
                        Icon(painter = painterResource(id = R.drawable.btn_miniplayer_next),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)
                                .clickable { nextSongPlayButtonClick() })
                        Icon(painter = painterResource(id = R.drawable.btn_miniplayer_go_list),
                            contentDescription = null,
                            modifier = Modifier
                                .size(45.dp)
                                .clickable { musicQueueClick() })
                    }
                }
            }
        }
    }?:run {
        Box(modifier = Modifier.fillMaxWidth()
            .padding(16.dp).background(Color.LightGray)){
            Text(text="현재 재생 중인 곡이 없습니다.",
                fontSize = 18.sp,
                color= Color.Gray,
                modifier = Modifier.align(Alignment.Center))
        }
    }





}

//@RequiresApi(Build.VERSION_CODES.P)
//@Preview(showBackground = true)
//@Composable
//fun PreviewMiniPlayer(){
//    val fakeSongViewModel = FakeSongViewModel(application = Application())
//    MiniPlayer(
//        viewModel = fakeSongViewModel,
//        beforeSongPlayButtonClick = { /*TODO*/ },
//        playSongButtonClick = { /*TODO*/ },
//        nextSongPlayButtonClick = { /*TODO*/ },
//        musicQueueClick = { /*TODO*/ },
//        toSongActivity = {})
//}

