package umc.study.umc_7th

import android.os.Bundle
import androidx.activity.ComponentActivity

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import umc.study.umc_7th.ui.composables.Album
import umc.study.umc_7th.ui.composables.BottomBar
import umc.study.umc_7th.ui.composables.TopButtonsView
import umc.study.umc_7th.ui.viewmodel.MockMusicViewModel
import umc.study.umc_7th.ui.viewmodel.MusicViewModel

@AndroidEntryPoint
class SongActivity : ComponentActivity() {
    private val viewModel: MusicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val songId = intent?.getIntExtra("song_id", 0)
        setContent {
            SongScreen(viewModel = viewModel, songId = songId)
        }
    }
}

@Composable
fun SongScreen(viewModel: MusicViewModel, songId: Int?) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopButtonsView()
        Album(viewModel = viewModel, songId = songId)
        BottomBar()
    }
}

@Preview
@Composable
fun PreviewSongscreen() {
    val mockViewModel = MockMusicViewModel()
    SongScreen(viewModel = mockViewModel, songId = 1)
}