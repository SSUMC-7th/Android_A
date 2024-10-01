package umc.study.umc_7th

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun MainBanner(
    title1 : String,
    title2 : String,
    date : LocalDate,
    contentList : List<Content>,
    backgroundImage: ImageBitmap,
    textColor : Color,
    MikeButtonClick : ()-> Unit,
    TicketButtonClick : ()-> Unit,
    SettingButtonClick : ()-> Unit,
    PlayButtonClick : () -> Unit
){
    Box{
        Image(bitmap = backgroundImage,
            contentDescription =null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize())
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                listOf(
                    MikeButtonClick to R.drawable.btn_main_mike,
                    TicketButtonClick to R.drawable.btn_main_ticket,
                    SettingButtonClick to R.drawable.btn_main_setting
                ).forEach { (onClick, icon) ->
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(end = 8.dp)
                            .clickable { onClick() }
                    )
                }
            }
            Column {
                Text(
                    text = title1,
                    style = TextStyle(
                        color = textColor,
                        fontSize = 29.sp,
                        fontWeight = FontWeight.Bold,
                        lineBreak = LineBreak.Heading,
                    )
                )
                Text(
                    text = title2,
                    style = TextStyle(
                        color = textColor,
                        fontSize = 29.sp,
                        fontWeight = FontWeight.Bold,
                        lineBreak = LineBreak.Heading,
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ){
                IconButton(onClick = PlayButtonClick,
                    modifier = Modifier.size(60.dp)
                ) {
                   Icon(
                       painter = painterResource(id = R.drawable.btn_panel_play_large),
                       contentDescription = null,
                       tint = textColor,
                   )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                Text(text = "총 ${contentList.size}곡",color =textColor)
                Text(text = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                    color = textColor,
                )
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(100.dp)
            ) {
                items(count = contentList.size) {index ->
                    val content = contentList[index]
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.clickable {  }
                    ){
                        content.image?.let {
                            Image(
                                bitmap = it,
                                contentDescription = null,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(text = content.title, color = textColor)
                            Text(text = content.author, color = textColor)

                        }
                    }
                }
            }
        }
    }


}

@RequiresApi(Build.VERSION_CODES.P)
@Preview
@Composable
fun PreviewBanner(){
    MainBanner(
        title1 = "포근하게 덮어주는 꿈의",
        title2 = "목소리",
        date = LocalDate.parse("2019-11-11"),
        contentList = List(15){
            Content(
                title = "Butter",
                author = "BTS",
                image = ImageBitmap.imageResource(id = R.drawable.img_album_exp),
                length = 200,
            )
        },
        backgroundImage = ImageBitmap.imageResource(id = R.drawable.img_default_4_x_1) ,
        textColor =Color.White ,
        MikeButtonClick = { /*TODO*/ },
        TicketButtonClick = { /*TODO*/ },
        SettingButtonClick = { /*TODO*/ }) {

    }
}