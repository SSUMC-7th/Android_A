package umc.study.umc_7th.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import umc.study.umc_7th.Album
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.R
import umc.study.umc_7th.VideoContent
import umc.study.umc_7th.databinding.FragmentHomeBinding
import umc.study.umc_7th.previewAlbumContent
import umc.study.umc_7th.previewMusicContentList
import umc.study.umc_7th.previewPodcastContentList
import umc.study.umc_7th.previewVideoContentList
import umc.study.umc_7th.ui.main.BottomNavigationBar
import umc.study.umc_7th.ui.main.MainViewModel
import umc.study.umc_7th.ui.main.NavigationDestination
import umc.study.umc_7th.ui.main.album.AlbumFragment
import umc.study.umc_7th.ui.theme.Umc_7thTheme
import java.time.LocalDate

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.composeViewHome.setContent {
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
        val bannerContents by viewModel.bannerContents.collectAsStateWithLifecycle()
        val albums by viewModel.albums.collectAsStateWithLifecycle()
        val podcasts by viewModel.podcasts.collectAsStateWithLifecycle()
        val videos by viewModel.videos.collectAsStateWithLifecycle()

        HomeScreen(
            bannerContents = bannerContents,
            albums = albums,
            podcasts = podcasts,
            videos = videos,
            onAlbumContentClicked = { album ->
                val albumFragment = AlbumFragment().also {
                    it.arguments = Bundle().apply { putLong("album_id", album.id) }
                }

                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.fragmentContainerView_main, albumFragment)
                    ?.addToBackStack(null)
                    ?.commit()
            },
            onAlbumPlayClicked = { album ->
                viewModel.getAlbum(
                    id = album.id,
                    onSuccess = { viewModel.setPlaylist(it.contentList) },
                    onFailed = { /* TODO */ }
                )
            },
            onMusicContentClicked = { viewModel.setCurrentContent(it) }
        )
    }
}

@Composable
private fun HomeScreen(
    bannerContents: List<List<MusicContent>>,
    albums: List<Album>,
    podcasts: List<PodcastContent>,
    videos: List<VideoContent>,
    onAlbumContentClicked: (Album) -> Unit,
    onAlbumPlayClicked: (Album) -> Unit,
    onMusicContentClicked: (MusicContent) -> Unit,
) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        MainBanner(
            date = LocalDate.parse("2019-11-11"),
            propsList = List(bannerContents.size) { index ->
                MainBannerProps(
                    title = "포근하게 덮어주는 꿈의 목소리",
                    contentList = bannerContents[index],
                    backgroundImage = ImageBitmap.imageResource(id = R.drawable.img_default_4_x_1),
                    onPlayButtonClicked = { /* TODO */ },
                )
            },
            onContentClicked = onMusicContentClicked,
            onVoiceSearchButtonClicked = { /* TODO */ },
            onSubscriptionButtonClicked = { /* TODO */ },
            onSettingButtonClicked = { /* TODO */ },
        )
        GlobeCategorizedAlbumCollectionView(
            title = "오늘 발매 음악",
            contentList = albums,
            globeCategory = GlobeCategory.GLOBAL,
            onViewTitleClicked = { /* TODO */ },
            onAlbumClicked = onAlbumContentClicked,
            onAlbumPlayClicked = onAlbumPlayClicked,
            onCategoryClicked = { /* TODO */ },
        )
        PromotionImageBanner(
            image = ImageBitmap.imageResource(id = R.drawable.img_home_viewpager_exp),
            onClicked = { /* TODO */ },
        )
        PodcastCollectionView(
            title = "매일 들어도 좋은 팟캐스트",
            contentList = podcasts,
            onContentClicked = { /* TODO */ },
        )
        VideoCollectionView(
            title = "비디오 콜렉션",
            contentList = videos,
            onContentClicked = { /* TODO */ },
        )
        PromotionImageBanner(
            image = ImageBitmap.imageResource(id = R.drawable.discovery_banner_aos),
            padding = 16.dp,
            onClicked = { /* TODO */ },
        )
        PromotionImageBanner(
            image = ImageBitmap.imageResource(id = R.drawable.img_home_viewpager_exp2),
            onClicked = { /* TODO */ },
        )
        Footer()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
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
                    currentContent = null,
                    isPlaying = false,
                    playingPoint = 50,
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                HomeScreen(
                    bannerContents = List(3) { previewMusicContentList },
                    albums = List(10) { previewAlbumContent.toAlbum() },
                    podcasts = previewPodcastContentList,
                    videos = previewVideoContentList,
                    onAlbumContentClicked = {},
                    onAlbumPlayClicked = {},
                    onMusicContentClicked = {},
                )
            }
        }
    }
}