package umc.study.umc_7th.locker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun SavedMusicPage() {
    // TODO: 구현
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "저장한 곡이 없습니다.",
            style = TextStyle(
                color = Color.Black.copy(alpha = 0.5f),
            )
        )
    }
}