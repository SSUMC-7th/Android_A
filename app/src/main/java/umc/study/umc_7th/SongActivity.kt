package umc.study.umc_7th

import android.content.Intent
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
import androidx.compose.material3.LinearProgressIndicator

class SongActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val songTitle = intent.getStringExtra("songtitle")
            val singer = intent.getStringExtra("author")
            Umc_7thTheme {
                SongPlayerScreen(
                    content =Content(
                        songTitle.toString(), singer.toString(),
                        ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
                        200
                    ) ,
                    playerSettingButtonClick = { /*TODO*/ },
                    eqButtonClick = { /*TODO*/ },
                    songActivityToMainActivity = {
                        val intent = Intent(this@SongActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    },
                    likeClick = { /*TODO*/ },
                    unLikeButtonClick = { /*TODO*/ },
                    replayButtonClick = { /*TODO*/ },
                    songPlayButtonClick = { /*TODO*/ },
                    beforeSongPlayClick = { /*TODO*/ },
                    nextSongPlayClick = { /*TODO*/ },
                    shuffleClick = { /*TODO*/ },
                    instagramShareClick = { /*TODO*/ },
                    similarSongButtonClick = { /*TODO*/ },
                    musicQueueClick = { /*TODO*/ }) {

                }
            }

        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SongPlayerScreen(
    content : Content,
    playerSettingButtonClick: () -> Unit,
    eqButtonClick: () -> Unit,
    songActivityToMainActivity : () -> Unit,
    likeClick : () -> Unit,
    unLikeButtonClick: () -> Unit,
    replayButtonClick: () -> Unit,
    songPlayButtonClick: () -> Unit,
    beforeSongPlayClick : () -> Unit,
    nextSongPlayClick : () -> Unit,
    shuffleClick: () -> Unit,
    instagramShareClick: () -> Unit,
    similarSongButtonClick : () -> Unit,
    musicQueueClick : () -> Unit,
    toSingerinfoClick: ()-> Unit,

){
    Column (modifier = Modifier
        .fillMaxHeight()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){  Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconButton(onClick = playerSettingButtonClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_player_setting),
                    contentDescription = null,

                    )
            }
            IconButton(onClick = eqButtonClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_player_eq_off),
                    contentDescription = null,
                    modifier = Modifier
                )
            }
        }

            IconButton(onClick = songActivityToMainActivity,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.nugu_btn_down),
                    contentDescription = null,
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = content.title , fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Row(
        ){
        Text(text = content.author, fontSize = 18.sp)
        Icon(painter = painterResource(id = R.drawable.btn_arrow_more),
            contentDescription = null,
            modifier = Modifier.clickable { toSingerinfoClick() })
        }
        Spacer(modifier = Modifier.height(20.dp))

        content.image?.let {
            Image(bitmap = it,
                contentDescription =null,
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(10.dp)))
        }
        Column(
            modifier = Modifier
                .width(120.dp)
                .height(50.dp),
            verticalArrangement = Arrangement.Center

        ) {
            Text(text = "너라는 꽃가루에 눈이 따끔해 아야", textAlign = TextAlign.Center)

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(24.dp),
            horizontalArrangement = Arrangement.Center
        ){
            IconButton(onClick =likeClick ) {
                Icon(bitmap = ImageBitmap.imageResource(id = R.drawable.ic_my_like_off),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 16.dp))
            }
            IconButton(onClick = unLikeButtonClick) {
                Icon(bitmap = ImageBitmap.imageResource(id = R.drawable.btn_player_unlike_off),
                    contentDescription = null)
            }
        }
        LinearProgressIndicator(progress = 0f,
            modifier = Modifier.fillMaxWidth().height(3.dp)
        )
        Row(

            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "00:00", fontSize = 12.sp)
            Text(text = "03:30", fontSize = 12.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ){
            listOf(
                replayButtonClick to R.drawable.nugu_btn_repeat_inactive,
                beforeSongPlayClick to R.drawable.nugu_btn_skip_previous_32,
                songPlayButtonClick to R.drawable.nugu_btn_play_32,
                nextSongPlayClick to R.drawable.nugu_btn_skip_next_32,
                shuffleClick to R.drawable.nugu_btn_random_inactive
                ).forEach { (onClick, icon) ->
                Icon(painter = painterResource(id = icon) ,
                    contentDescription =null,
                    modifier = Modifier
                        .size(65.dp)
                        .padding(10.dp)
                        .clickable { onClick() })
            }
        }
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween

            ){
            listOf(
                instagramShareClick to R.drawable.btn_actionbar_instagram,
                similarSongButtonClick to R.drawable.btn_player_related,
                musicQueueClick to R.drawable.btn_player_go_list
            ).forEach { (onClick, icon) ->
                Icon(painter = painterResource(id = icon),
                    contentDescription =null,
                    modifier = Modifier
                        .size(36.dp)
                        .clickable { onClick() })
            }

        }
    }
}
@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewSongPlayerScreen(){
    SongPlayerScreen(
        content = Content(
            "LILAC", "IU",
            ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
            200
        ),
        playerSettingButtonClick = { /*TODO*/ },
        eqButtonClick = { /*TODO*/ },
        songActivityToMainActivity = {},
        likeClick = { /*TODO*/ },
        unLikeButtonClick = { /*TODO*/ },
        replayButtonClick = { /*TODO*/ },
        songPlayButtonClick = { /*TODO*/ },
        beforeSongPlayClick = { /*TODO*/ },
        nextSongPlayClick = { /*TODO*/ },
        shuffleClick = { /*TODO*/ },
        instagramShareClick = { /*TODO*/ },
        similarSongButtonClick = { /*TODO*/ },
        musicQueueClick = {},
        toSingerinfoClick = {/*TODO*/},)
}


