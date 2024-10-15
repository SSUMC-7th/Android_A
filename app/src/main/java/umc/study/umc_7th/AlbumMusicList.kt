package umc.study.umc_7th

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AlbumMusicList(
    album : Album,
    playButtonClick : () -> Unit,
    playAllButtonClick : () -> Unit,
    selectAllButtonClick : () -> Unit,
    moreInfoButtonClick :() -> Unit,
    mixButtonClick : () -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxHeight()
    ) {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally) // 여기에 background 컬러 추가해야함.
                ) {
//                    Box(
//                    ){
//                        Button(onClick =mixButtonClick,
//                            colors = ButtonDefaults.buttonColors(
//                                contentColor = colorResource(id =),
//                            )) {
//
//                        }
                        Text(text = "내 취향 MIX", fontSize = 15.sp)

                        var checked by remember { mutableStateOf(false) }
                        Icon(painter = painterResource(
                            id = if (checked == false) R.drawable.btn_toggle_off
                            else R.drawable.btn_toggle_on
                        ), contentDescription = null,
                            modifier = Modifier
                                .clickable { checked = !checked }
                                .size(20.dp))
//                    }

                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    var selected by remember { mutableStateOf(false) }
                    Row(
                        modifier = Modifier
                            .clickable { selectAllButtonClick() }
                            .align(Alignment.CenterStart)

                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (selected == false) R.drawable.btn_playlist_select_off
                                else R.drawable.btn_playlist_select_on
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "전체 선택",
                            fontSize = 12.sp,
                            color = if (selected == false) Color.Black else Color.Blue
                        )
                    }
                    Row(
                        modifier = Modifier
                            .clickable { playAllButtonClick() }
                            .align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_browse_arrow_right),
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "전체 듣기", color = Color.Black,
                            fontSize = 12.sp
                        )
                    }


                }
            }

        }

        itemsIndexed(album.trackList) { index, track ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "${index + 1}", Modifier.padding(horizontal = 4.dp),
                    fontSize = 12.sp, fontWeight = FontWeight.Bold
                )
                Column {
                    Text(text = track)
                    Text(
                        text = album.author,
                        color = Color.Black.copy(0.5f),
                        fontSize = 12.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(painter = painterResource(id = R.drawable.btn_player_play),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { playButtonClick() })
                    Icon(painter = painterResource(id = R.drawable.btn_player_more),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { moreInfoButtonClick() })
                }
            }


        }


    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewAlbumMusicList(){
    AlbumMusicList(
        album = Album(
            albumTitle = "IU 5th Album 'LILAC'",
            date = LocalDate.parse("2023-03-27"),
            author = "IU(아이유)",
            albumImage =ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
            trackList = listOf("LILAC", "Coin", "Flu", "Troll", "Lovesick"),
            titleTrackList = listOf("LILAC", "Flu")
        ),
        playButtonClick = { /*TODO*/ },
        playAllButtonClick = { /*TODO*/ },
        selectAllButtonClick = { /*TODO*/ },
        mixButtonClick = {},
        moreInfoButtonClick = {}
    )
}