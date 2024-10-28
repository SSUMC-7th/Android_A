package umc.study.umc_7th.album

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import umc.study.umc_7th.Album
import umc.study.umc_7th.BottomNavigationBar
import umc.study.umc_7th.NavigationDestination
import umc.study.umc_7th.R
import umc.study.umc_7th.getTestMusicContentList

class AlbumFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val album = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            arguments?.getSerializable("album", Album::class.java)!!
        else
            arguments?.getSerializable("album")!! as Album

        return inflater.inflate(R.layout.fragment_album, container, false).apply {
            findViewById<ComposeView>(R.id.composeView_album).setContent {
                AlbumScreen(
                    album = album,
                    onBackButtonClicked = {
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.remove(this@AlbumFragment)
                            ?.commit()
                        activity?.supportFragmentManager?.popBackStack()
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun AlbumScreen(
    album: Album,
    onBackButtonClicked: () -> Unit,
) {
    // 이 화면은 목업입니다.
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        TopButtonBar(
            isLiked = false,
            onBackButtonClicked = onBackButtonClicked,
            onLikeButtonClicked = {},
            onDetailsButtonClicked = {}
        )
        AlbumFrame(
            title = album.title,
            author = album.author,
            cover = album.imageBitmap,
            releasedDate = album.releasedDate,
            type = album.type,
            genre = album.genre,
        )
        TabLayout(
            tabs = listOf(
                TabItem(
                    label = "수록곡",
                    page = {
                        IncludedContentsPage(
                            contentList = album.contentList.map {
                                ContentWithLabel(
                                    content = it.first,
                                    label = it.second,
                                )
                            },
                            isMixed = false,
                            onPlayContentClicked = {},
                            onContentDetailsClicked = {},
                            onMixButtonClicked = {},
                            onPlayAllButtonClicked = {},
                        )
                    },
                ),
                TabItem(
                    label = "상세정보",
                    page = {
                        AlbumDetailsPage()
                    },
                ),
                TabItem(
                    label = "영상",
                    page = {
                        VideosPage()
                    },
                ),
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewAlbumScreen() {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onPlaylistButtonClicked = {},
                onDestinationClicked = {},
                onPreviousButtonClicked = {},
                onNextButtonClicked = {},
                onPlayButtonClicked = {},
                onContentClicked = {},
                currentDestination = NavigationDestination.HOME,
                currentContent = getTestMusicContentList((1..4).random()).random(),
                isPlaying = false,
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AlbumScreen(
                album = getTestMusicContentList((1..4).random()).random().album,
                onBackButtonClicked = {}
            )
        }
    }
}