package umc.study.umc_7th

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun PlayProgressBar(
    viewModel : SongViewModel,
    beforeSongPlayClick : () -> Unit,
    songPlayButtonClick: () -> Unit,
    nextSongPlayClick : ()-> Unit,

){
    val replay by viewModel.replay.observeAsState(false)
    val shuffle by viewModel.shuffle.observeAsState(false)

    val played by viewModel.played.observeAsState(false)
    val currentPosition by viewModel.currentPosition.observeAsState(0f)
    val duration by viewModel.duration.observeAsState(1f)

    var progress by remember { mutableStateOf(0f) }

//    LaunchedEffect(played){
//        if(played){
//            while ( played && currentPosition < duration){
//                delay(1000L)
//                viewModel.updatePosition(currentPosition+1f)
//            }
//        }
//    }
    progress = currentPosition / duration
    Slider(
        value = progress,
        onValueChange = { newProgress ->
            viewModel.updatePosition(newProgress * duration)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(3.dp),
        thumb = {

        },
    )
    Row(

        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp)
    ){
        Text(text = formatTime(currentPosition), fontSize = 12.sp, color= Color.Blue)
        Text(text = formatTime(duration), fontSize = 12.sp)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.Center,
    ){

        Icon(
            painter = painterResource(id = R.drawable.nugu_btn_repeat_inactive),
            contentDescription = null,
            modifier = Modifier
                .size(65.dp)
                .padding(10.dp)
                .clickable { viewModel.toggleReplay() }
            , tint = if (replay) Color.Blue else Color.Black
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.nugu_btn_skip_previous_32),
            contentDescription = null,
            modifier = Modifier
                .size(65.dp)
                .padding(10.dp)
                .clickable { beforeSongPlayClick()
                }

        )

        Icon(
            painter = painterResource(id = if(played) R.drawable.nugu_btn_pause_32
            else R.drawable.nugu_btn_play_32),
            contentDescription = null,
            modifier = Modifier
                .size(65.dp)
                .padding(10.dp)
                .clickable {
                    viewModel.togglePlayed()
                    songPlayButtonClick()
                }
        )

        Icon(
            painter = painterResource(id = R.drawable.nugu_btn_skip_next_32),
            contentDescription = null,
            modifier = Modifier
                .size(65.dp)
                .padding(10.dp)
                .clickable { nextSongPlayClick() }
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.nugu_btn_random_inactive),
            contentDescription = null,
            modifier = Modifier
                .size(65.dp)
                .padding(10.dp)
                .clickable { viewModel.toggleShuffle() },
            tint = if (shuffle) Color.Blue else Color.Black
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlayProgressBar(){
    val fakeSongViewModel = FakeSongViewModel(application = Application())
    PlayProgressBar(
        viewModel = fakeSongViewModel,
        beforeSongPlayClick = { /*TODO*/ },
        songPlayButtonClick = { /*TODO*/ },
        nextSongPlayClick = {}
    )
}