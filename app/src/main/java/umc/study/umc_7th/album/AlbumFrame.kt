package umc.study.umc_7th.album

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun AlbumFrame(
    title: String,
    author: String,
    cover: ImageBitmap,
    releasedDate: LocalDate,
    type: String,
    genre: String,
) {
    var coverWidth by remember { mutableStateOf(0.dp) }
    var coverSideWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
            )
            Text(
                text = author,
                style = TextStyle(
                    color = Color.Black.copy(alpha = 0.5f),
                ),
            )
            Text(
                text = "${
                    DateTimeFormatter.ofPattern("yyyy.MM.dd").format(releasedDate)
                } | $type | $genre",
                style = TextStyle(
                    color = Color.Black.copy(alpha = 0.5f),
                ),
            )
        }
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(
                    modifier = Modifier
                        .let { if (coverWidth.value > 0) it.animateContentSize() else it }
                        .width(coverSideWidth.plus(coverWidth))
                )
                Image(
                    painter = painterResource(id = R.drawable.img_album_lp),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(coverWidth.times(0.9f))
                        .onGloballyPositioned {
                            with(density) { coverSideWidth = it.size.width.toDp() }
                        },
                    contentDescription = null,
                )
            }
            Image(
                bitmap = cover,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(8.dp))
                    .onGloballyPositioned {
                        with(density) { coverWidth = it.size.width.toDp() }
                    },
                contentDescription = null,
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAlbumFrame() {
    AlbumFrame(
        title = "IU 5th Album \"LILAC\"",
        author = "아이유(IU)",
        cover = ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
        releasedDate = LocalDate.parse("2021-03-25"),
        type = "정규",
        genre = "댄스 팝",
    )
}