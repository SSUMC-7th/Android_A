package umc.study.umc_7th.main.album

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun AlbumDetailsPage() {
    // TODO: 구현
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth().height(100.dp)
    ) {
        Text(
            text = "상세 정보가 없습니다.",
            style = TextStyle(
                color = Color.Black.copy(alpha = 0.5f),
            )
        )
    }
}