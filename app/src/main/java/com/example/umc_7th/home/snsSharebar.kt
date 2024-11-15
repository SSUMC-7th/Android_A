package com.example.umc_7th.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.umc_7th.R

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun snsSharebar(
    facebookClick : ()-> Unit,
    instagramClick : () -> Unit,
    youtubeClick : ()-> Unit,
    twitterClick : ()-> Unit,
){
    Row (
        modifier = Modifier.fillMaxWidth()
    ){
        Spacer(modifier = Modifier.width(70.dp))
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
                    .weight(0.7f)
                    .clickable { onClick() }
            )
        }
        Spacer(modifier = Modifier.width(50.dp))
    }
}
@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewsnsSharebar(){
    snsSharebar(
        facebookClick = { /*TODO*/ },
        instagramClick = { /*TODO*/ },
        youtubeClick = { /*TODO*/ },
        twitterClick = { /* */})
}



