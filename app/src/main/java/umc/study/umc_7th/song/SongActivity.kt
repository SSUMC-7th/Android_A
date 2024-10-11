package umc.study.umc_7th.song

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import umc.study.umc_7th.Content
import umc.study.umc_7th.getTestMusicContentList
import umc.study.umc_7th.ui.theme.Umc_7thTheme
import java.time.LocalDateTime
import java.time.ZoneOffset

class SongActivity: ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun SongScreen(
    content: Content,
    onMinimizeButtonClicked: () -> Unit,
) {
    var playingPoint by remember(content) { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = content) {
        scope.launch {
            val startTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
            while (true) {
                playingPoint = (LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - startTime).toInt()
                delay(100)
            }
        }
    }

    var isPlaying by remember { mutableStateOf(false) }
    var isRepeating by remember { mutableStateOf(false) }
    var isShuffling by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(false) }
    var isBlocked by remember { mutableStateOf(false) }

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
            playingPoint = playingPoint,
            isPlaying = isPlaying,
            isRepeating = isRepeating,
            isShuffling = isShuffling,
            isLiked = isLiked,
            isBlocked = isBlocked,
            onLikeButtonClicked = { isLiked = it },
            onBlockButtonClicked = { isBlocked = it },
            onRepeatButtonClicked = { isRepeating = it },
            onShuffleButtonClicked = { isShuffling = it },
            onPreviousButtonClicked = {},
            onNextButtonClicked = {},
            onPlayButtonClicked = { isPlaying = it },
            onPlayingPointChanged = {},
        )
        FooterActionBar(
            onPlaylistButtonClicked = {},
            onInstagramButtonClicked = {},
            onSimilarSongButtonClicked = {},
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSongScreen() {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            SongScreen(
                content = getTestMusicContentList((1..4).random()).random(),
                onMinimizeButtonClicked = {}
            )
        }
    }
}