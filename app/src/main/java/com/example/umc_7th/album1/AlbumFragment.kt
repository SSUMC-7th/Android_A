package com.example.umc_7th.album1

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import java.time.LocalDate

// AlbumData 데이터 클래스 정의
data class AlbumData(
    val albumTitle: String,
    val date: LocalDate,
    val author: String,
    val albumImage: Int,
    val trackList: List<String>,
    val titleTrackList: List<String>
)

@Composable
fun AlbumInfo() {
    Text(text = "앨범 정보")
}

@Composable
fun AlbumVideo() {
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
) {
    // AlbumData 객체 생성
    val album = AlbumData(
        albumTitle = albumTitle,
        date = date,
        author = author,
        albumImage = albumImage,
        trackList = trackList,
        titleTrackList = titleTrackList
    )

    Column {
        // AlbumFragmentTop 호출
        AlbumFragmentTop(
            album = album,
            albumFgtoMain = { navController.navigate("homeFragment") },
            likeButtonClick = { /* TODO: 좋아요 버튼 기능 추가 */ },
            playerMoreButtonClick = { /* TODO: 더보기 버튼 기능 추가 */ }
        )

        // TabLayout 호출
        TabLayout(album = album)
    }
}

// AlbumFragmentTop 함수 정의
@Composable
fun AlbumFragmentTop(
    album: AlbumData,
    albumFgtoMain: () -> Unit,
    likeButtonClick: () -> Unit,
    playerMoreButtonClick: () -> Unit
) {
    Column {
        Text(text = "Album Title: ${album.albumTitle}")
        Text(text = "Author: ${album.author}")
        Text(text = "Date: ${album.date}")
        // 버튼 클릭 이벤트 추가
    }
}

// TabLayout 함수 정의
@Composable
fun TabLayout(album: AlbumData) {
    Column {
        Text(text = "TabLayout for ${album.albumTitle}")
        // Tab 항목 구성
    }
}



