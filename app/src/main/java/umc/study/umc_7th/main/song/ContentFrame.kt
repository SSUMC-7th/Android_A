package umc.study.umc_7th.main.song

import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.R
import umc.study.umc_7th.SongViewModel
import umc.study.umc_7th.content.ContentDao
import umc.study.umc_7th.content.ContentRepository

@Composable
fun ContentFrame(

    viewModel: SongViewModel,
    toSingerinfoClick: ()-> Unit,
    likeClick : () -> Unit,
    unLikeButtonClick: () -> Unit

){
    val currentSong by viewModel.currentSong.observeAsState()

    val unLike by viewModel.unLike.observeAsState(false)

    currentSong?.let{ content ->
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = content.title ,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold)

            Row(
            ){
                Text(text = content.author, fontSize = 18.sp)
                Icon(painter = painterResource(id = R.drawable.btn_arrow_more),
                    contentDescription = null,
                    modifier = Modifier.clickable { toSingerinfoClick() })
            }
            Spacer(modifier = Modifier.height(18.dp))

            content.image?.let {
                Image(bitmap = ImageBitmap.imageResource(id = it),
                    contentDescription =null,
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(20.dp)))
            }
            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(36.dp),
                horizontalArrangement = Arrangement.Center,

                ){
                val context = LocalContext.current
                IconButton(onClick = {
                    viewModel.toggleLike(content)
                    if(content.islike) Toast.makeText(context, "Unlike", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(context, "Like", Toast.LENGTH_SHORT).show()
                } ) {
                    Image(bitmap = ImageBitmap.imageResource(id = if(content.islike) R.drawable.ic_my_like_on
                    else R.drawable.ic_my_like_off
                    ),
                        contentDescription = null,
                        contentScale= ContentScale.Crop,
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                IconButton(onClick = unLikeButtonClick) { // unlikeButtonClick에서는 더 복잡한 것들을 처리
                    Icon(bitmap = ImageBitmap.imageResource(id = if(unLike) R.drawable.btn_player_unlike_off
                    else R.drawable.btn_player_unlike_on
                    ),
                        contentDescription = null,
                        modifier = Modifier.clickable { viewModel.toggleUnLike() }) // 단순한 toggle 상태만을 관찰하도록.
                }
            }
        }
    } ?:run {
        Text(text = "곡 정보가 없습니다.", fontSize = 16.sp,
            color = Color.Gray)
    }


}


//
//@Composable
//@Preview(showBackground = true)
//fun PreviewContentFrame(){
//    ContentFrame(
//
//        toSingerinfoClick = {},
//        likeClick = {},
//        unLikeButtonClick = {},
//        viewModel = SongViewModel(application = Application(),
//            ContentRepository(contentDao = )
//        )
//    )
//}