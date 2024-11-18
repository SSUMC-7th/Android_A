package com.example.umc_7th.song

import android.app.Application
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.umc_7th.R
import com.example.umc_7th.etc.FakeSongViewModel
import com.example.umc_7th.etc.SongViewModel

@Composable
fun ContentFrame(
    viewModel: SongViewModel,
    toSingerinfoClick: () -> Unit,
) {
    // LiveData 관찰
    val currentSong by viewModel.currentSong.observeAsState()
    val like by viewModel.like.observeAsState(false)
    val unLike by viewModel.unLike.observeAsState(false)

    currentSong?.let { content ->
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = content.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Row {
                Text(text = content.author, fontSize = 18.sp)
                Icon(
                    painter = painterResource(id = R.drawable.btn_arrow_more),
                    contentDescription = null,
                    modifier = Modifier.clickable { toSingerinfoClick() }
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

            content.image?.let { imageId ->
                Image(
                    painter = painterResource(id = imageId), // 수정된 부분
                    contentDescription = null,
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(36.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { viewModel.toggleLike() }) {
                    Image(
                        painter = painterResource(id = if (like) R.drawable.ic_my_like_on else R.drawable.ic_my_like_off),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                IconButton(onClick = { viewModel.toggleUnLike() }) {
                    Icon(
                        painter = painterResource(id = if (unLike) R.drawable.btn_player_unlike_off else R.drawable.btn_player_unlike_on),
                        contentDescription = null
                    )
                }
            }
        }
    } ?: run {
        Text(
            text = "곡 정보가 없습니다.",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewContentFrame() {
    // FakeSongViewModel을 사용하여 Preview에서 테스트
    ContentFrame(
        viewModel = FakeSongViewModel(Application()),
        toSingerinfoClick = {},
    )
}



