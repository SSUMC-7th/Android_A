package umc.study.umc_7th

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun MainSnsSharebar(
    facebookClick : ()-> Unit,
    instagramClick : () -> Unit,
    youtubeClick : ()-> Unit,
    twitterClick : ()-> Unit,
){
    Row (
        modifier = Modifier.fillMaxWidth()
    ){
        Spacer(modifier = Modifier.width(50.dp))
        listOf(
            facebookClick to R.drawable.ic_main_facebook_btn,
            instagramClick to R.drawable.ic_main_instagram,
            youtubeClick to R.drawable.ic_main_youtube_btn,
            twitterClick to R.drawable.ic_main_twitter_btn
        ).forEach { (onClick, icon)->
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
                    .weight(0.5f)
                    .clickable { onClick() }
            )
        }
        Spacer(modifier = Modifier.width(50.dp))
    }
}

@Composable
fun SongSnsBar(
    instagramShareClick: () -> Unit,
    similarSongButtonClick : () -> Unit,
    musicQueueClick : () -> Unit,
){
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



@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewsnsSharebar(){
    Column {
        MainSnsSharebar(
            facebookClick = { /*TODO*/ },
            instagramClick = { /*TODO*/ },
            youtubeClick = { /*TODO*/ },
            twitterClick = { /* */})

        SongSnsBar(
            instagramShareClick = { /*TODO*/ },
            similarSongButtonClick = { /*TODO*/ },
            musicQueueClick = {})
    }

}