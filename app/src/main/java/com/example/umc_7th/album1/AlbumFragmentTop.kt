// AlbumFragmentTop.kt 파일
package com.example.umc_7th.album1

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc_7th.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun AlbumFragmentTop(
    album: Album,
    albumFgtoMain: () -> Unit,
    likeButtonClick: () -> Unit,
    playerMoreButtonClick: () -> Unit,
    playAlbumButtonClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = albumFgtoMain) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.btn_arrow_black),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                var like by remember { mutableStateOf(false) }
                IconButton(onClick = likeButtonClick) {
                    Icon(
                        bitmap = if (like) ImageBitmap.imageResource(id = R.drawable.ic_my_like_on)
                        else ImageBitmap.imageResource(id = R.drawable.ic_my_like_off),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable { like = !like },
                        tint = Color.Unspecified
                    )
                }

                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.btn_player_more),
                    contentDescription = null,
                    modifier = Modifier.clickable { playerMoreButtonClick() }
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = album.albumTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = album.author,
                style = TextStyle(
                    color = Color.Black.copy(alpha = 0.5f)
                ),
                fontSize = 12.sp
            )
            val dateString = album.date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
            val genre = "댄스 팝"
            val regular = "정규"
            Text(text = "$dateString | $regular | $genre")
        }

        Row(
            modifier = Modifier.padding(start = 70.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(id = album.albumImage),
                    contentDescription = null,
                    modifier = Modifier
                        .size(210.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(20.dp))
                )
                IconButton(onClick = playAlbumButtonClick, modifier = Modifier.align(Alignment.BottomEnd)) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(id = R.drawable.widget_black_play),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                }
            }
            Image(
                painter = painterResource(id = R.drawable.img_album_lp),
                contentDescription = null
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
@Preview(showBackground = true)
fun previewAlbumFragmentTop() {
    AlbumFragmentTop(
        album = Album(
            albumTitle = "IU 5th Album 'LILAC'",
            date = LocalDate.parse("2023-03-27"),
            author = "IU(아이유)",
            albumImage = R.drawable.img_album_exp2,
            trackList = listOf("LILAC", "Coin", "Flu", "Troll", "Lovesick"),
            titleTrackList = listOf("LILAC", "Flu")
        ),
        albumFgtoMain = { /* TODO: 메인 화면으로 이동하는 함수 구현 */ },
        likeButtonClick = { /* TODO: 좋아요 클릭 처리 함수 구현 */ },
        playerMoreButtonClick = { /* TODO: 플레이어 더보기 클릭 처리 함수 구현 */ },
        playAlbumButtonClick = { /* TODO: 앨범 재생 클릭 처리 함수 구현 */ }
    )
}


