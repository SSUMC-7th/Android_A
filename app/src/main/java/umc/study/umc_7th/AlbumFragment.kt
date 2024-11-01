package umc.study.umc_7th

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable


import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape


import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text


import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
//import com.google.accompanist.pager.*

import kotlinx.coroutines.launch


import java.time.LocalDate
import java.time.format.DateTimeFormatter



@Composable
fun AlbumInfo(){
    Text(text ="앨범 정보")
}

@Composable
fun AlbumVideo(){
    Text(text = "앨범 영상")
}


@SuppressLint("NewApi")
@Composable
fun albumFragment(
    navController: NavController,
    albumTitle: String,
    albumImage: Int,
    author: String,
    date: LocalDate,
    trackList: List<String>,
    titleTrackList: List<String>
){
    Column() {
        AlbumFragmentTop(
            album = Album(
                albumTitle = albumTitle,
                date = date,
                author = author,
                albumImage = albumImage,
                trackList = trackList,
                titleTrackList = titleTrackList
            ),
//            album= Album,
            date = date,
            albumFgtoMain = { navController.navigate("homeFragment") },
            likeButtonClick = { /*TODO*/ },
            playerMoreButtonClick = { /*TODO*/ }) {

        }
        TabLayout()
    }

}
