package umc.study.umc_7th

import android.os.Bundle
import androidx.activity.ComponentActivity

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


class SongActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SongScreen()
        }
    }
}

@Composable
fun SongScreen() {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopButtonsView()
        Album()
        BottomBar()
    }
}

@Preview
@Composable
fun PreviewSongscreen() {
    SongScreen()
}