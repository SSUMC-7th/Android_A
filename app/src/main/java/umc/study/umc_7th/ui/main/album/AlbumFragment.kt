package umc.study.umc_7th.ui.main.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import umc.study.umc_7th.AlbumContent
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.R
import umc.study.umc_7th.previewAlbumContent
import umc.study.umc_7th.previewMusicContentList
import umc.study.umc_7th.ui.main.BottomNavigationBar
import umc.study.umc_7th.ui.main.MainActivity
import umc.study.umc_7th.ui.main.MainViewModel
import umc.study.umc_7th.ui.main.NavigationDestination

class AlbumFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private val contentPlayerService get() = (requireActivity() as MainActivity).contentPlayerService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val albumId = arguments?.getLong("album_id")
        if (albumId != null) viewModel.getAlbum(albumId) {}

        return inflater.inflate(R.layout.fragment_album, container, false).apply {
            findViewById<ComposeView>(R.id.composeView_album).setContent {
                AlbumScreen(
                    album = viewModel.currentAlbum,
                    onPlayContentClicked = {
                        contentPlayerService.setContent(it)
                    },
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

@Composable
private fun AlbumScreen(
    album: AlbumContent?,
    onPlayContentClicked: (MusicContent) -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    var isMixed by remember { mutableStateOf(false) }

    // 이 화면은 목업입니다.
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        TopButtonBar(
            isLiked = false,
            onBackButtonClicked = onBackButtonClicked,
            onLikeButtonClicked = { /* TODO */ },
            onDetailsButtonClicked = { /* TODO */ }
        )
        if (album != null) AlbumFrame(
            title = album.title,
            author = album.author,
            imageId = album.imageId,
            releasedDate = album.releasedDate,
            type = album.type,
            genre = album.genre,
        )
        TabLayout(
            tabs = listOf(
                TabItem(
                    label = "수록곡",
                    page = {
                        if (album != null) IncludedMusicsPage(
                            contentList = album.contentList,
                            isMixed = isMixed,
                            onPlayContentClicked = onPlayContentClicked,
                            onContentDetailsClicked = { /* TODO */ },
                            onMixButtonClicked = { isMixed = it },
                            onPlayAllButtonClicked = { /* TODO */ },
                        )
                    },
                ),
                TabItem(
                    label = "상세정보",
                    page = { AlbumDetailsPage() },
                ),
                TabItem(
                    label = "영상",
                    page = { VideosPage() },
                ),
            )
        )
    }
}

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
                currentContent = previewMusicContentList.random(),
                isPlaying = false,
                playingPoint = 50,
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AlbumScreen(
                album = previewAlbumContent,
                onBackButtonClicked = {},
                onPlayContentClicked = {},
            )
        }
    }
}