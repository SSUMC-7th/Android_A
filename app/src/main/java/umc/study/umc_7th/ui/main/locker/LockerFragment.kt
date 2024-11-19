package umc.study.umc_7th.ui.main.locker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import umc.study.umc_7th.AlbumContent
import umc.study.umc_7th.Content
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.databinding.FragmentLockerBinding
import umc.study.umc_7th.previewMusicContentList
import umc.study.umc_7th.ui.login.LoginActivity
import umc.study.umc_7th.ui.main.BottomNavigationBar
import umc.study.umc_7th.ui.main.MainViewModel
import umc.study.umc_7th.ui.main.NavigationDestination
import umc.study.umc_7th.ui.theme.Umc_7thTheme
import javax.inject.Inject

@AndroidEntryPoint
class LockerFragment : Fragment() {
    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentLockerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        binding.composeViewLocker.setContent {
            Umc_7thTheme {
                Scaffold { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Screen()
                    }
                }
            }
        }

        return binding.root
    }

    @Composable
    private fun Screen() {
        LockerScreen(
            likedContents = viewModel.likedContents,
            savedMusics = viewModel.savedMusics,
            onPlayContent = { viewModel.play(it) },
            onDeleteSavedMusics = { musics ->
                musics.forEach {
                    viewModel.deleteMusic(
                        music = it,
                        onFailed = { /* TODO */ }
                    )
                }
            },
            onCancelLikes = { contents ->
                contents.forEach { content ->
                    viewModel.setLike(
                        id = content.id,
                        like = false,
                        onFailed = { /* TODO */ }
                    )
                }
            },
            onPlayAlbum = { /* TODO */ },
            onDeleteSavedAlbum = { /* TODO */ },
            onLogout = {
                viewModel.logout(
                    onSuccess = {
                        val intent = Intent(requireContext(), LoginActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()
                    },
                    onFailed = { /* TODO */ }
                )
            }
        )
    }
}

@Composable
fun LockerScreen(
    likedContents: List<Content>,
    savedMusics: List<MusicContent>,
    onPlayContent: (Content) -> Unit,
    onPlayAlbum: (AlbumContent) -> Unit,
    onDeleteSavedAlbum: (List<AlbumContent>) -> Unit,
    onDeleteSavedMusics: (List<MusicContent>) -> Unit,
    onCancelLikes: (List<Content>) -> Unit,
    onLogout: () -> Unit,
) {
    val tabs = remember(likedContents, savedMusics) {
        listOf(
            TabItem(
                label = "좋아요",
                page = {
                    LikedContentsPage (
                        contents = likedContents,
                        onPlayAllButtonClicked = { /* TODO */ },
                        onPlayButtonClicked = onPlayContent,
                        onCancelLikeClicked = { onCancelLikes(listOf(it)) },
                        onDetailsClicked = { /* TODO */ },
                        onCancelLikesClicked = onCancelLikes,
                    )
                }
            ),
            TabItem(
                label = "저장한 곡",
                page = {
                    SavedMusicsPage(
                        musics = savedMusics,
                        onPlayAllButtonClicked = { /* TODO */ },
                        onPlayButtonClicked = onPlayContent,
                        onDeleteClicked = { onDeleteSavedMusics(listOf(it)) },
                        onDetailsClicked = { /* TODO */ },
                        onDeleteMusics = onDeleteSavedMusics,
                    )
                }
            ),
            TabItem(
                label = "음악파일",
                page = { StorageFilePage() }
            ),
            TabItem(
                label = "저장앨범",
                page = {
                    SavedAlbumsPage(
                        albums = listOf(),
                        onPlayAllButtonClicked = { /* TODO */ },
                        onPlayButtonClicked = onPlayAlbum,
                        onCancelLikeClicked = { onDeleteSavedAlbum(listOf(it)) },
                        onDetailsClicked = { /* TODO */ },
                        onCancelLikesClicked = onCancelLikes,
                    )
                }
            ),
        )
    }

    Column {
        TitleBar(
            title = "보관함",
            onLogoutClicked = onLogout
        )
        TabLayout(tabs = tabs)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLockerScreen() {
    Umc_7thTheme {
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
                LockerScreen(
                    likedContents = previewMusicContentList,
                    savedMusics = previewMusicContentList,
                    onPlayContent = {},
                    onDeleteSavedMusics = {},
                    onCancelLikes = {},
                    onDeleteSavedAlbum = {},
                    onPlayAlbum = {},
                    onLogout = {},
                )
            }
        }
    }
}
