package com.example.umc_7th.album1

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import umc.study.umc_7th.Album
import umc.study.umc_7th.TabLayout
import java.time.LocalDate

// Album 데이터 클래스 정의 (예시)
data class Album(
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
    Column {
        // AlbumFragmentTop 호출 부분 수정
        AlbumFragmentTop(
            album = Album(
                albumTitle = albumTitle,
                date = date,
                author = author,
                albumImage = albumImage,
                trackList = trackList,
                titleTrackList = titleTrackList
            ),
            date = date,
            albumFgtoMain = { navController.navigate("homeFragment") },
            likeButtonClick = { /* TODO: 좋아요 버튼 기능 추가 */ },
            playerMoreButtonClick = { /* TODO: 더보기 버튼 기능 추가 */ }
        )

        // TabLayout 호출 부분 수정
        TabLayout(
            album = Album(
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

// AlbumFragmentTop 함수 정의 (예시)
@Composable
fun AlbumFragmentTop(
    album: Album,
    date: LocalDate,
    albumFgtoMain: () -> Unit,
    likeButtonClick: () -> Unit,
    playerMoreButtonClick: () -> Unit
) {
    // AlbumFragmentTop의 UI 구성
    Column {
        Text(text = "Album Title: ${album.albumTitle}")
        Text(text = "Author: ${album.author}")
        Text(text = "Date: ${album.date}")
        // 버튼 클릭 이벤트 등 추가
    }
}

// TabLayout 함수 정의 (예시)
@Composable
fun TabLayout(album: Album) {
    // TabLayout의 UI 구성
    Column {
        Text(text = "TabLayout for ${album.albumTitle}")
        // Tab 항목 구성
    }
}
