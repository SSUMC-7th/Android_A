package umc.study.umc_7th.ui.song

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import umc.study.umc_7th.Content
import umc.study.umc_7th.ContentPlayerService
import umc.study.umc_7th.R
import umc.study.umc_7th.previewMusicContentList
import umc.study.umc_7th.ui.theme.Umc_7thTheme
import java.util.concurrent.CountDownLatch

@AndroidEntryPoint
class SongActivity: ComponentActivity() {
    private val viewModel: SongViewModel by viewModels()
    lateinit var contentPlayerService: ContentPlayerService
    private val serviceBound = CountDownLatch(1)

    private val connection = object: ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as ContentPlayerService.LocalBinder
            contentPlayerService = binder.getService()
            serviceBound.countDown()
        }

        override fun onServiceDisconnected(arg0: ComponentName) = Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        Intent(this, ContentPlayerService::class.java).also {
            startService(it)
            bindService(it, connection, Context.BIND_AUTO_CREATE)
        }

        lifecycleScope.launch(Dispatchers.IO) {
            serviceBound.await()
            viewModel.getMusic(
                id = contentPlayerService.getContent()?.id ?: -1,
                onFailed = { /* TODO */ }
            )
            setScreen()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }
    
    private fun setScreen() {
        val composeView = findViewById<ComposeView>(R.id.composeView_song)
        composeView.setContent {
            Umc_7thTheme {
                Scaffold { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        val isPlaying by contentPlayerService.isPlaying.collectAsStateWithLifecycle()
                        val playingPoint by contentPlayerService.playingPoint.collectAsStateWithLifecycle()

                        SongScreen(
                            content = viewModel.content,
                            playingPoint = playingPoint ?: 0,
                            isPlaying = isPlaying,
                            isLiked = viewModel.isLiked,
                            onMinimizeButtonClicked = { finish() },
                            onPlayButtonClicked = {
                                contentPlayerService.let { service ->
                                    if (it) service.resume()
                                    else service.pause()
                                }
                            },
                            onPlayingPointChanged = {
                                contentPlayerService.seek(it)
                            },
                            onLikeButtonClicked = {
                                viewModel.setLike(it, onFailed = { /* TODO */ })
                            },
                            onSaveClicked = {
                                viewModel.save(onFailed = { /* TODO */ })
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SongScreen(
    content: Content?,
    playingPoint: Int,
    isPlaying: Boolean,
    isLiked: Boolean,
    onMinimizeButtonClicked: () -> Unit,
    onPlayButtonClicked: (Boolean) -> Unit,
    onPlayingPointChanged: (Int) -> Unit,
    onLikeButtonClicked: (Boolean) -> Unit,
    onSaveClicked: () -> Unit,
) {
    var isRepeating by remember { mutableStateOf(false) }
    var isShuffling by remember { mutableStateOf(false) }
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
            onSettingButtonClicked = { /* TODO */ },
            onEqualizerButtonClicked = { /* TODO */ },
            onMinimizeButtonClicked = onMinimizeButtonClicked,
            onDetailsClicked = { /* TODO */ },
            onSaveClicked = onSaveClicked,
        )
        if (content != null) ContentFrame(
            title = content.title,
            author = content.author,
            imageId = content.imageId,
            onAuthorNameClicked = { /* TODO */ },
        )
        LyricsView(
            line1 = "내리는 꽃가루에",
            line2 = "눈이 따끔해 아야",
            onClicked = { /* TODO */ },
        )
        if (content != null) PlayProgressControlPanel(
            length = content.length,
            playingPoint = playingPoint,
            isPlaying = isPlaying,
            isRepeating = isRepeating,
            isShuffling = isShuffling,
            isLiked = isLiked,
            isBlocked = isBlocked,
            onLikeButtonClicked = onLikeButtonClicked,
            onBlockButtonClicked = { isBlocked = it },
            onRepeatButtonClicked = { isRepeating = it },
            onShuffleButtonClicked = { isShuffling = it },
            onPreviousButtonClicked = { /* TODO */ },
            onNextButtonClicked = { /* TODO */ },
            onPlayButtonClicked = onPlayButtonClicked,
            onPlayingPointChanged = onPlayingPointChanged,
        )
        FooterActionBar(
            onPlaylistButtonClicked = { /* TODO */ },
            onInstagramButtonClicked = { /* TODO */ },
            onSimilarSongButtonClicked = { /* TODO */ },
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSongScreen() {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            val content = previewMusicContentList.random()

            SongScreen(
                content = content,
                playingPoint = 20,
                isPlaying = true,
                isLiked = true,
                onMinimizeButtonClicked = {},
                onPlayButtonClicked = {},
                onPlayingPointChanged = {},
                onLikeButtonClicked = {},
                onSaveClicked = {},
            )
        }
    }
}