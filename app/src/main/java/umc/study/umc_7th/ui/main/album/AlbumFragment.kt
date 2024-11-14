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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import umc.study.umc_7th.AlbumContent
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.R
import umc.study.umc_7th.databinding.FragmentAlbumBinding
import umc.study.umc_7th.previewAlbumContent
import umc.study.umc_7th.previewMusicContentList
import umc.study.umc_7th.ui.main.BottomNavigationBar
import umc.study.umc_7th.ui.main.MainViewModel
import umc.study.umc_7th.ui.main.NavigationDestination

class AlbumFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentAlbumBinding
    private val album = MutableStateFlow<AlbumContent?>(null)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        val albumId = arguments?.getLong("album_id")
        if (albumId != null) viewModel.getAlbum(
            id = albumId,
            onSuccess = { album.value = it },
            onFailed = { /* TODO */ },
        )

        binding.composeViewAlbum.setContent { Screen() }
        return binding.root
    }

    @Composable
    private fun Screen() {
        val album by this@AlbumFragment.album.collectAsStateWithLifecycle()

        AlbumScreen(
            album = album,
            onPlayContentClicked = { viewModel.play(it) },
            onBackButtonClicked = {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.remove(this@AlbumFragment)
                    ?.commit()
                activity?.supportFragmentManager?.popBackStack()
            }
        )
    }
}

@Composable
private fun AlbumScreen(
    album: AlbumContent?,
    onPlayContentClicked: (MusicContent) -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    var isMixed by remember { mutableStateOf(false) }

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