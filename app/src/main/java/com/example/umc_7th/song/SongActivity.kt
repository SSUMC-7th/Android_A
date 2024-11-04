package com.example.umc_7th.song

import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import umc.study.umc_7th.ui.theme.Umc_7thTheme
import androidx.activity.ComponentActivity
import androidx.compose.foundation.rememberScrollState
import umc.study.umc_7th.FakeSongViewModel
import umc.study.umc_7th.MyApplication
import umc.study.umc_7th.SongViewModel
import umc.study.umc_7th.main.home.MainActivity
import umc.study.umc_7th.main.home.SongSnsBar

class SongActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = (application as MyApplication).songViewModel

        setContent {

            Umc_7thTheme {
                Box(){
                    SongPlayerScreen(
                        viewModel= viewModel,
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
        toMainActivity = {},
    )
}