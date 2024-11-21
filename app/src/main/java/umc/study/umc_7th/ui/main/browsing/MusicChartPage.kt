package umc.study.umc_7th.ui.main.browsing

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.R
import umc.study.umc_7th.SuspendedImage
import umc.study.umc_7th.previewMusicContentList
import java.time.LocalDateTime
import kotlin.math.abs
import kotlin.random.Random

@Composable
fun MusicChartPage(
    chart: List<Pair<MusicContent, Int>>,
    baseTime: LocalDateTime,
    onTitleClicked: () -> Unit,
    onPlayAllButtonClicked: () -> Unit,
    onPlayContentClicked: (MusicContent) -> Unit,
    onContentDetailsClicked: (MusicContent) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = onTitleClicked) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = "차트", style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                )
                Icon(
                    painter = painterResource(id = R.drawable.btn_arrow_more),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Gray,
                )
            }
        }

        ElevatedCard(
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.White
            ), elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 8.dp
            ), shape = RoundedCornerShape(16.dp), modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = "FLO 차트", style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                            )
                        )
                        Text(
                            text = "${baseTime.hour}시 기준", style = TextStyle(
                                color = Color.Black.copy(0.5f),
                            )
                        )
                    }

                    TextButton(
                        onClick = onPlayAllButtonClicked, contentPadding = PaddingValues(2.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(16.dp),
                            painter = painterResource(id = R.drawable.btn_editbar_play),
                            contentDescription = null,
                            tint = Color.Black
                        )
                        Text(
                            text = "전체듣기", style = TextStyle(
                                fontSize = 12.sp,
                                color = Color.Black,
                            )
                        )
                    }
                }

                Column {
                    chart.subList(0, 5).forEachIndexed { index, (music, change) ->
                        MusicItem(
                            music = music,
                            index = index,
                            change = change,
                            onPlayButtonClicked = { onPlayContentClicked(music) },
                            onDetailsButtonClicked = { onContentDetailsClicked(music) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MusicItem(
    music: MusicContent,
    index: Int,
    change: Int,
    onPlayButtonClicked: () -> Unit,
    onDetailsButtonClicked: () -> Unit,
) {
    Box {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)
            ) {
                Column {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),
                    ) {
                        Text(
                            text = "%02d".format(index + 1), style = TextStyle(
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp),
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontSize = 8.sp)) {
                                    append(if (change > 0) "▲" else if (change < 0) "▼" else "●")
                                }
                                withStyle(style = SpanStyle(fontSize = 4.sp)) {
                                    append(" ")
                                }
                                withStyle(style = SpanStyle(fontSize = 12.sp)) {
                                    append(abs(change).toString())
                                }
                            },
                            style = TextStyle(
                                color = if (change > 0) Color.Green
                                else if (change < 0) Color.Red
                                else Color.Gray,
                                fontSize = 8.sp,
                            ),
                            maxLines = 1,
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    SuspendedImage(id = music.imageId) {
                        it?.let {
                            Image(
                                bitmap = it,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            )
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.height(24.dp)
                        ) {
                            music.label?.let { label ->
                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = Color.Blue,
                                            shape = RoundedCornerShape(percent = 50)
                                        )
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = label,
                                        style = TextStyle(
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        ),
                                    )
                                }
                            }
                            Text(
                                text = music.title,
                                maxLines = 1,
                                overflow = TextOverflow.Clip,
                                modifier = Modifier.weight(1f),
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.height(20.dp),
                        ) {
                            Text(
                                text = music.author,
                                style = TextStyle(
                                    color = Color.Black.copy(0.5f), fontSize = 12.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Clip,
                            )
                        }
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                IconButton(
                    onClick = onPlayButtonClicked, modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_player_play),
                        contentDescription = null
                    )
                }
                IconButton(
                    onClick = onDetailsButtonClicked, modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.btn_player_more),
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChartPage() {
    MusicChartPage(
        chart = previewMusicContentList.map { it to Random.nextInt(-10, 10) },
        baseTime = LocalDateTime.now(),
        onTitleClicked = {},
        onPlayAllButtonClicked = {},
        onPlayContentClicked = {},
        onContentDetailsClicked = {},
    )
}