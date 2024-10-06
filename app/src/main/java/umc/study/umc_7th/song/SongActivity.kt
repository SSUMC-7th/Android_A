package umc.study.umc_7th.song

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import umc.study.umc_7th.Content
import umc.study.umc_7th.getTestContentList
import umc.study.umc_7th.ui.theme.Umc_7thTheme

class SongActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val content = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra("content", Content::class.java)
        else
            intent.getSerializableExtra("content") as? Content

        setContent {
            Umc_7thTheme {
                Scaffold { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        content?.let {
                            SongScreen(
                                content = it,
                                onMinimizeButtonClicked = {
                                    finish()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SongScreen(
    content: Content,
    onMinimizeButtonClicked: () -> Unit,
) {
    // 이 스크린은 목업용 화면입니다.
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TopButtonBar(
            onSettingButtonClicked = {},
            onEqualizerButtonClicked = {},
            onMinimizeButtonClicked = onMinimizeButtonClicked,
            onDetailsButtonClicked = {},
        )
        ContentFrame(
            title = content.title,
            author = content.author,
            cover = content.imageBitmap,
            onAuthorNameClicked = {},
        )
        LyricsView(
            line1 = "내리는 꽃가루에",
            line2 = "눈이 따끔해 아야",
            onClicked = {},
        )
        PlayProgressControlPanel(
            length = 181,
            playingPoint = 125,
            isPlaying = true,
            isRepeating = true,
            isShuffling = false,
            isLiked = true,
            isBlocked = false,
            onLikeButtonClicked = {},
            onBlockButtonClicked = {},
            onRepeatButtonClicked = {},
            onShuffleButtonClicked = {},
            onPreviousButtonClicked = {},
            onNextButtonClicked = {},
            onPlayButtonClicked = {},
            onPlayingPointChanged = {},
        )
        FooterActionBar(
            onPlaylistButtonClicked = {},
            onInstagramButtonClicked = {},
            onSimilarSongButtonClicked = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSongScreen() {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            SongScreen(
                content = getTestContentList().random(),
                onMinimizeButtonClicked = {}
            )
        }
    }
}