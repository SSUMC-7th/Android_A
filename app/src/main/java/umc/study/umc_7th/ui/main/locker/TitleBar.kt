package umc.study.umc_7th.ui.main.locker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleBar(
    title: String,
    isLoginDone: Boolean,
    onLoginClicked: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )

        if (isLoginDone) {
            // TODO: 로그인되었을 시 화면을 구현
        }
        else {
            ClickableText(
                text = AnnotatedString("로그인"),
                style = TextStyle(
                    color = Color.Blue,
                ),
                onClick = { onLoginClicked() },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTitleBar() {
    TitleBar(
        title = "보관함",
        isLoginDone = false,
        onLoginClicked = {},
    )
}