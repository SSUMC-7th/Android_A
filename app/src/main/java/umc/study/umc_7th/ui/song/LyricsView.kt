package umc.study.umc_7th.ui.song

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LyricsView(
    line1: String,
    line2: String,
    onClicked: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Button(
            onClick = onClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Black.copy(0.5f)
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(text = line1)
                Text(text = line2)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLyricView() {
    LyricsView(
        line1 = "내리는 꽃가루에",
        line2 = "눈이 따끔해 아야",
        onClicked = {},
    )
}