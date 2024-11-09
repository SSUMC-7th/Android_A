package umc.study.umc_7th.album

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*


import androidx.compose.material3.Text


import androidx.navigation.NavController
//import com.google.accompanist.pager.*

import umc.study.umc_7th.content.Album
import umc.study.umc_7th.TabLayout


import java.time.LocalDate


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
        TabLayout(album =
        Album(
            albumTitle = albumTitle,
            date = date,
            author = author,
            albumImage = albumImage,
            trackList = trackList,
            titleTrackList = titleTrackList
        )
        )
    }

}
