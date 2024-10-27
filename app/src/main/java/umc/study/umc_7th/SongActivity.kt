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


class SongActivity : ComponentActivity() {
    private val viewModel: MusicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SongScreen(viewModel = viewModel)
        }
    }
}

@Composable
fun SongScreen(viewModel: MusicViewModel) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopButtonsView()
        Album(viewModel = viewModel)
        BottomBar()
    }
}

@Preview
@Composable
fun PreviewSongscreen() {
    SongScreen(viewModel = MusicViewModel())
}