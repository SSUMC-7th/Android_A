package umc.study.umc_7th.home

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
import umc.study.umc_7th.BottomNavigationBar
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.NavigationDestination
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.R
import umc.study.umc_7th.VideoContent
import umc.study.umc_7th.album.AlbumFragment
import umc.study.umc_7th.getTestMusicContentList
import java.time.LocalDate

class HomeFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false).apply {
            findViewById<ComposeView>(R.id.composeView_home).setContent {
                HomeScreen(
                    onMusicContentClicked = { content ->
                        val albumFragment = AlbumFragment()
                        val bundle = Bundle()
                        bundle.putSerializable("album", content.album)
                        albumFragment.arguments = bundle
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.fragmentContainerView_main, albumFragment)
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun HomeScreen(
    onMusicContentClicked: (MusicContent) -> Unit,
) {
    // 이 스크린은 목업용 화면입니다.
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        MainBanner(
            date = LocalDate.parse("2019-11-11"),
            propsList = listOf(
                MainBannerProps(
                    title = "포근하게 덮어주는 꿈의 목소리",
                    contentList = getTestMusicContentList((1..4).random()),
                    backgroundImage = ImageBitmap.imageResource(id = R.drawable.img_default_4_x_1),
                    onContentClicked = onMusicContentClicked,
                    onPlayButtonClicked = {},
                ),
                MainBannerProps(
                    title = "달밤의 감성 산책",
                    contentList = getTestMusicContentList((1..4).random()),
                    backgroundImage = ImageBitmap.imageResource(id = R.drawable.img_jenre_exp_1),
                    onContentClicked = {},
                    onPlayButtonClicked = {},
                )
            ),
            onVoiceSearchButtonClicked = {},
            onSubscriptionButtonClicked = {},
            onSettingButtonClicked = {},
        )
        GlobeCategorizedMusicCollectionView(
            title = "오늘 발매 음악",
            contentList = getTestMusicContentList((1..4).random()),
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
            contentList = List(15) {
                PodcastContent(
                    title = "김시선의 귀책사유 FLO X 윌라",
                    author = "김시선",
                    imageId = R.drawable.img_potcast_exp,
                    length = 200,
                )
            },
            onContentClicked = { /* TODO */ },
        )
        VideoCollectionView(
            title = "비디오 콜렉션",
            contentList = List(15) {
                VideoContent(
                    title = "제목",
                    author = "지은이",
                    imageId = R.drawable.img_video_exp,
                    length = 200,
                )
            },
            onContentClicked = { /* TODO */ },
        )
        PromotionImageBanner(
            image = ImageBitmap.imageResource(id = R.drawable.discovery_banner_aos),
            padding = 16.dp,
            onClicked = {},
        )
        PromotionImageBanner(
            image = ImageBitmap.imageResource(id = R.drawable.img_home_viewpager_exp2),
            onClicked = {},
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
                currentContent = getTestMusicContentList((1..4).random()).random(),
                isPlaying = false,
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HomeScreen(
                onMusicContentClicked = {}
            )
        }
    }
}