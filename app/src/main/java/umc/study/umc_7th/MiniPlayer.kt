package umc.study.umc_7th

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun MiniPlayer(
    content: Content,
    beforeSongPlayButtonClick: () -> Unit,
    playSongButtonClick: () -> Unit,
    nextSongPlayButtonClick: () -> Unit,
    musicQueueClick: () -> Unit,
    toSongActivity: (Content) -> Unit,
){
    Box(modifier = Modifier.clickable {toSongActivity(content) }
    )
    {
        Row(
            modifier = Modifier
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
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                listOf(
                    beforeSongPlayButtonClick to R.drawable.btn_miniplayer_previous,
                    playSongButtonClick to R.drawable.btn_miniplayer_play,
                    nextSongPlayButtonClick to R.drawable.btn_miniplayer_next,
                    musicQueueClick to R.drawable.btn_miniplayer_go_list
                ).forEach { (onClick, icon) ->
                    Icon(painter = painterResource(id = icon),
                        contentDescription =null,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable { onClick() })
                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewMiniPlayer(){
    MiniPlayer(
        content = Content(
            title = "LILAC",
            author = "IU",
            image = ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
            length = 200
        ),
        beforeSongPlayButtonClick = { /*TODO*/ },
        playSongButtonClick = { /*TODO*/ },
        nextSongPlayButtonClick = { /*TODO*/ },
        musicQueueClick = { /*TODO*/ },
        toSongActivity = {})
}

