package umc.study.umc_7th

import android.app.Application
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import umc.study.umc_7th.ui.theme.Umc_7thTheme
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay

class SongActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = (application as MyApplication).songViewModel

        setContent {
            val songTitle = intent.getStringExtra("songtitle")
            val singer = intent.getStringExtra("author")
            Umc_7thTheme {
                    Box(){
                        SongPlayerScreen(
                            viewModel= viewModel,
                            content =Content(
                                songTitle.toString(), singer.toString(),
                                R.drawable.img_album_exp2,
                                200
                            ) ,
                            toMainActivity = {
                                val intent = Intent(this@SongActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            },)
                    }




            }

        }
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SongPlayerScreen(
    viewModel: SongViewModel,
    content : Content,
    toMainActivity : () -> Unit,
    ){
    Column (modifier = Modifier
        .verticalScroll(rememberScrollState())
        .fillMaxHeight()
        .padding(8.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
        SongTopButton(
            SettingButtonClick = {},
            eqButtonClick = {},
            songActivityToMainActivity = { toMainActivity() }
        )

        ContentFrame(
            content = content,
            toSingerinfoClick={},
            likeClick ={},
            unLikeButtonClick={},
            viewModel = viewModel
        )

        Lyric(lyric1 = "내리는 꽃가루에", lyric2 = "눈이 따끔해 아야")

        PlayProgressBar(
            viewModel = viewModel,
            beforeSongPlayClick = { /*TODO*/ },
            songPlayButtonClick = { /*TODO*/ },
            nextSongPlayClick = {}
        )
        SongSnsBar(
            instagramShareClick = { /*TODO*/ },
            similarSongButtonClick = { /*TODO*/ },
            musicQueueClick ={}
        )
        }
    }


fun formatTime(timeInSeconds:Float) : String{
    val minutes = (timeInSeconds / 60).toInt()
    val seconds = (timeInSeconds % 60).toInt()
    return String.format("%02d:%02d", minutes, seconds)
}


@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewSongPlayerScreen(){
    val fakeviewModel = FakeSongViewModel(application = Application())
    SongPlayerScreen(
        viewModel = fakeviewModel,
        content = Content(
            "LILAC", "IU",
            R.drawable.img_album_exp2,
            200
        ),
        toMainActivity = {},
        )
}


