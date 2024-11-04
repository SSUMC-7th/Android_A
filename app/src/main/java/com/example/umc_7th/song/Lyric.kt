package com.example.umc_7th.song

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Lyric(
    lyric1 : String,
    lyric2 : String,
){
    Column(
        modifier = Modifier
            .width(120.dp)
            .height(50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally


    ) {
        Text(text = lyric1, textAlign = TextAlign.Center)
        Text(text = lyric2, textAlign =TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLyric(){
    Lyric(lyric1 = "내리는 꽃가루에", lyric2 = "눈이 따끔해 아야")

}