package umc.study.umc_7th.locker

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.R
import umc.study.umc_7th.SongViewModel
import umc.study.umc_7th.content.AlbumContent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LockerAlbum(
    showBottomBar: () -> Unit,
    hideBottomBar: () -> Unit,
    selectAllButtonClick: () -> Unit,
    playAllButtonClick: () -> Unit,
    albumList: List<AlbumContent>,
    viewModel: SongViewModel
) {
    val allSelect by viewModel.selectAll.observeAsState(initial = false)
    val mutableAlbumList = remember { mutableStateListOf<AlbumContent>().apply { addAll(albumList) } }
    val itemStates = remember { mutableStateMapOf<AlbumContent, Boolean>().apply { mutableAlbumList.forEach { put(it, false) } } }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Top Row (전체 선택, 전체 듣기)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            Row {
                // Select All
                Row(
                    modifier = Modifier.clickable {
                        viewModel.SelectAll()
                        if (allSelect) {
                            mutableAlbumList.forEach { itemStates[it] = true }
                            showBottomBar()
                        } else {
                            mutableAlbumList.forEach { itemStates[it] = false }
                            hideBottomBar()
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (allSelect == false) R.drawable.btn_playlist_select_off
                            else R.drawable.btn_playlist_select_on
                        ),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(23.dp)
                    )
                    Text(
                        text = "전체 선택",
                        fontSize = 16.sp,
                        color = if (allSelect == false) Color.Black.copy(0.5f) else Color.Blue
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Play All
                Row(
                    modifier = Modifier.clickable { playAllButtonClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_browse_arrow_right),
                        contentDescription = null,
                        modifier = Modifier.size(23.dp)
                    )
                    Text(
                        text = "전체 듣기",
                        color = Color.Black.copy(0.5f),
                        fontSize = 16.sp
                    )
                }
            }

            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "편집", fontSize = 16.sp, color = Color.Black.copy(0.5f))
                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        // Album List
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {

        }
    }
}


