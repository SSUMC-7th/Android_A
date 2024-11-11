package umc.study.umc_7th.locker

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import umc.study.umc_7th.content.Content
import umc.study.umc_7th.R
import umc.study.umc_7th.SongViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LockerMusic(
    selectAllButtonClick:()-> Unit,
    playAllButtonClick:()-> Unit,
    contentList : List<Content>,


    ){
    var contentList by remember{ mutableStateOf(contentList.toMutableList())}



    Column (
        modifier = Modifier
            .fillMaxSize()

    ){
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, vertical = 12.dp)) {

            var selected by remember { mutableStateOf(false) }
            Row(){
                Row(
                    modifier = Modifier
                        .clickable { selectAllButtonClick() }


                ) {

                    Icon(
                        painter = painterResource(
                            id = if (selected == false) R.drawable.btn_playlist_select_off
                            else R.drawable.btn_playlist_select_on
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(23.dp)
                    )
                    Text(
                        text = "전체 선택",
                        fontSize = 16.sp,
                        color = if (selected == false) Color.Black.copy(0.5f) else Color.Blue
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Row(
                    modifier = Modifier
                        .clickable { playAllButtonClick() }
                    ,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_browse_arrow_right),
                        contentDescription = null,
                        modifier = Modifier.size(23.dp)
                    )
                    Text(
                        text = "전체 듣기", color = Color.Black.copy(0.5f),
                        fontSize = 16.sp
                    )
                }
            }

            Row(
                modifier= Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = "편집", fontSize = 16.sp, color = Color.Black.copy(0.5f))
                Spacer(modifier = Modifier.width(8.dp))
            }

        }

        val itemStates = remember { mutableStateMapOf<Content, Boolean>().apply { contentList.forEach { put(it, false) } } }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(contentList){ content ->
                val isSelected = itemStates[content]?:false
                Box(
                    modifier= Modifier.fillMaxWidth()
                        .background(if (!isSelected) Color.Unspecified else Color.Blue.copy(0.05f))
                        .clickable { itemStates[content] = !isSelected }

                ){
                    Row(
                        modifier = Modifier.padding(horizontal =8.dp)
                    ){
                        content.image?.let {
                            Image(bitmap = ImageBitmap.imageResource(id = it),
                                contentDescription =null,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(3.dp)))
                        }
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(horizontal = 8.dp)
                        ) {
                            Text(text= content.title,
                                modifier= Modifier.padding(bottom = 2.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp)
                            Text(text= content.author,
                                color= Color.Black.copy(0.5f),
                                fontSize= 14.sp)
                        }

                    }
                    Row(
                        modifier = Modifier.align(Alignment.CenterEnd).padding(8.dp)
                    ){
                        Icon(bitmap= ImageBitmap.imageResource(id = R.drawable.btn_player_play),
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                        )
                        Icon(bitmap= ImageBitmap.imageResource(id = R.drawable.btn_player_more),
                            contentDescription = null,
                            modifier= Modifier.size(30.dp)
                                .clickable { contentList = contentList.filter { it != content }.toMutableList() })
                    }
                }
                Spacer(modifier = Modifier.padding(5.dp))
            }

        }
    }


}