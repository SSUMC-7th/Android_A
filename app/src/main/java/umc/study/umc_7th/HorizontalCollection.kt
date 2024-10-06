package umc.study.umc_7th

import android.icu.text.UnicodeSetSpanner.CountMethod
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate


enum class BaseLocationCategory(
    val mean : String
){
    GLOABAL(mean = "종합"),
    DOMESTIC(mean = "국내"),
    FOREIGN(mean = "해외"),
}
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun LocationMusicContentView(
    title : String,
    contentList : List<Album>,
    baseLocationCategory: BaseLocationCategory,
    viewTitleClick : () -> Unit,
    contentClick : (Album) -> Unit,
    categoryClick : (BaseLocationCategory) -> Unit,
){
    horizontalScrollAlbumContentView(
        contentList = contentList,
        titleBar = {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 5.dp)
                    .fillMaxWidth()
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.clickable { viewTitleClick() }
                ){
                    Text(
                        text = title,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.btn_main_arrow_more),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    BaseLocationCategory.entries.forEach{ category ->
                        ClickableText(
                            text = AnnotatedString(category.mean),
                            onClick = { categoryClick(category)},
                            style = TextStyle(
                                color = if(category == baseLocationCategory) Color.Blue else Color.Unspecified
                            )
                        )
                    }
                }
            }
        },
        thumbnail = { content ->
            Box(
                contentAlignment = Alignment.BottomEnd,
            ){

                    Image(bitmap = content.albumImage,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(128.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )

                Icon(
                    painter = painterResource(id = R.drawable.btn_miniplayer_play),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
            }
        },
        contentClick = contentClick,
    )
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun PodcastCollectionView(
    title : String,
    contentList: List<Content>,
    contentClick: (Content) -> Unit,
){
    horizontalScrollContentView(
        contentList = contentList,
        titleBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        },
        thumbnail = { content ->
            content.image?.let {
                Image(
                    bitmap = ImageBitmap.imageResource(id = content.image),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        },
        contentClick = contentClick,
    )
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun VideoCollectionView(
    title : String,
    contentList : List<Content>,
    contentClick: (Content) -> Unit,
){
    horizontalScrollContentView(contentList =contentList ,
        titleBar = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ){
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                )
            }
        },
        thumbnail = { content ->
            Box(contentAlignment = Alignment.BottomEnd){
                content.image?.let {
                    Image(bitmap = ImageBitmap.imageResource(id =content.image),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .width(228.dp)
                            .height(128.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .background(color = Color.Black.copy(alpha = 0.5f))
                ){
                    Text(
                        text = "${"%02d".format(content.length/60)}:${content.length%60}",
                        style = TextStyle(
                            fontSize = 10.sp,
                            color = Color.White,
                        ),
                        modifier = Modifier.padding(4.dp),
                    )
                }
            }
        },
        contentClick= contentClick
    )
}
@RequiresApi(Build.VERSION_CODES.P)
@Composable
private fun horizontalScrollAlbumContentView(
    contentList: List<Album>,
    titleBar : @Composable () -> Unit,
    thumbnail : @Composable (Album) -> Unit,
    contentClick: (Album) -> Unit
) {
    val density = LocalDensity.current
    var contentWidth by remember { mutableStateOf(0.dp) }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(vertical = 18.dp)
    ) {
        titleBar()
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ){
            item { Spacer(modifier = Modifier.width(0.dp))}
            items(count = contentList.size){ index ->
                val content = contentList[index]
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.clickable { contentClick(content) }
                ) {
                    Box(
                        modifier = Modifier.onGloballyPositioned {
                            with(density) { contentWidth = it.size.width.toDp() }
                        }
                    ){
                        thumbnail(content)
                    }
                    Column (verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.width(contentWidth)
                    ) {
                        Text(
                            text = content.albumTitle,
                            style = TextStyle(
                                fontSize = 18.sp,
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = content.author,
                            style = TextStyle(
                                color = TextStyle.Default.color.copy(alpha = 0.5f)
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            item { Spacer(modifier = Modifier.width(0.dp))}
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
private fun horizontalScrollContentView(
    contentList: List<Content>,
    titleBar : @Composable () -> Unit,
    thumbnail : @Composable (Content) -> Unit,
    contentClick: (Content) -> Unit
) {
    val density = LocalDensity.current
    var contentWidth by remember { mutableStateOf(0.dp) }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(vertical = 18.dp)
    ) {
        titleBar()
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ){
            item { Spacer(modifier = Modifier.width(0.dp))}
            items(count = contentList.size){ index ->
                val content = contentList[index]
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.clickable { contentClick(content) }
                ) {
                    Box(
                        modifier = Modifier.onGloballyPositioned {
                            with(density) { contentWidth = it.size.width.toDp() }
                        }
                    ){
                        thumbnail(content)
                    }
                    Column (verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.width(contentWidth)
                    ) {
                       Text(
                           text = content.title,
                           style = TextStyle(
                               fontSize = 18.sp,
                           ),
                           maxLines = 1,
                           overflow = TextOverflow.Ellipsis
                       )
                       Text(
                           text = content.author,
                           style = TextStyle(
                               color = TextStyle.Default.color.copy(alpha = 0.5f)
                           ),
                           maxLines = 1,
                           overflow = TextOverflow.Ellipsis
                       )
                    }
                }
            }
            item { Spacer(modifier = Modifier.width(0.dp))}
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewLocationMusicContentView(){
    LocationMusicContentView(
        title = "오늘 발매 음악",
        contentList = List(15){
            Album(
                albumTitle = "IU 5th Album 'LILAC'",
                date = LocalDate.parse("2023-03-27"),
                author = "IU(아이유)",
                albumImage = ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
                trackList = listOf("LILAC", "Coin", "Flu", "Troll", "Lovesick"),
                titleTrackList = listOf("LILAC", "Flu")
            )
        },
        baseLocationCategory = BaseLocationCategory.GLOABAL ,
        viewTitleClick = { },
        contentClick ={}, // 여기에 프래그먼트 전환기능 추가해야됨
        categoryClick = {}
    )
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewPodcastCollectionView(){
    PodcastCollectionView(
        title = "매일 들어도 좋은 팟캐스트" ,
        contentList = List(15){
            Content(
                title = "김시선의 귀책사유 FLO X 윌라",
                author= "김시선",
                image = R.drawable.img_potcast_exp,
                length = 200,
            )
        },
        contentClick = {}
    )
}
@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewVideoCollectionView(){
    VideoCollectionView(
        title ="비디오 콜렉션" ,
        contentList =List(15){
            Content(
                title = "제목",
                author = "지은이",
                image = R.drawable.img_video_exp,
                length = 200,
            )
        } ,
        contentClick = {}
    )
}