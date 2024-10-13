package umc.study.umc_7th.main.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.R
import umc.study.umc_7th.VideoContent
import umc.study.umc_7th.main.BottomNavigationBar
import umc.study.umc_7th.main.MainActivity
import umc.study.umc_7th.main.MainViewModel
import umc.study.umc_7th.main.NavigationDestination
import umc.study.umc_7th.previewMusicContentList
import umc.study.umc_7th.previewPodcastContentList
import umc.study.umc_7th.previewVideoContentList
import java.time.LocalDate

class HomeFragment : Fragment() {
    private val viewModel by activityViewModels<MainViewModel>()
    private val contentPlayerService get() = (requireActivity() as MainActivity).contentPlayerService

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false).apply {
            findViewById<ComposeView>(R.id.composeView_home).setContent {
                HomeScreen(
                    bannerContents = viewModel.bannerContents,
                    musics = viewModel.musics,
                    podcasts = viewModel.podcasts,
                    videos = viewModel.videos,
                    onMusicContentClicked = lambda@ { content ->
                        contentPlayerService.setContent(content)
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun HomeScreen(
    bannerContents: List<List<MusicContent>>,
    musics: List<MusicContent>,
    podcasts: List<PodcastContent>,
    videos: List<VideoContent>,
    onMusicContentClicked: (MusicContent) -> Unit,
) {
    // 이 스크린은 목업용 화면입니다.
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        MainBanner(
            date = LocalDate.parse("2019-11-11"),
            propsList = List(bannerContents.size) { index ->
                MainBannerProps(
                    title = "포근하게 덮어주는 꿈의 목소리",
                    contentList = bannerContents[index],
                    backgroundImage = ImageBitmap.imageResource(id = R.drawable.img_default_4_x_1),
                    onPlayButtonClicked = {},
                )
            },
            onContentClicked = { /* TODO */ },
            onVoiceSearchButtonClicked = {},
            onSubscriptionButtonClicked = {},
            onSettingButtonClicked = {},
        )
        GlobeCategorizedMusicCollectionView(
            title = "오늘 발매 음악",
            contentList = musics,
            globeCategory = GlobeCategory.GLOBAL,
            onViewTitleClicked = {},
            onContentClicked = onMusicContentClicked,
            onCategoryClicked = {},
        )
        PromotionImageBanner(
            image = ImageBitmap.imageResource(id = R.drawable.img_home_viewpager_exp),
            onClicked = {},
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
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
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HomeScreen(
                bannerContents = listOf(
                    previewMusicContentList,
                    previewMusicContentList,
                    previewMusicContentList,
                ),
                musics = previewMusicContentList,
                podcasts = previewPodcastContentList,
                videos = previewVideoContentList,
                onMusicContentClicked = {},
            )
        }
    }
}