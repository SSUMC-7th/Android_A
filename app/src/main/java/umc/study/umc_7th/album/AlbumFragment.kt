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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import umc.study.umc_7th.BottomNavigationBar
import umc.study.umc_7th.Content
import umc.study.umc_7th.NavigationDestination
import umc.study.umc_7th.R
import umc.study.umc_7th.getTestContentList
import java.time.LocalDate

class AlbumFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album, container, false).apply {
            findViewById<ComposeView>(R.id.composeView_album).setContent {
                AlbumScreen(
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
            title = "IU 5th Album \"LILAC\"",
            author = "아이유(IU)",
            cover = ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
            releasedDate = LocalDate.parse("2021-03-25"),
            type = "정규",
            genre = "댄스 팝",
        )
        TabLayout(
            tabs = listOf(
                TabItem(
                    label = "수록곡",
                    page = {
                        IncludedContentsPage(
                            contentList = getTestContentList().mapIndexed { index, content ->
                                ContentWithTitleLabel(
                                    content = content,
                                    isTitle = index == 0,
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
                currentContent = Content(
                    title = "Butter",
                    author = "BTS",
                    imageId = R.drawable.img_album_exp,
                    length = 200,
                ),
                isPlaying = false,
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AlbumScreen(
                onBackButtonClicked = {}
            )
        }
    }
}