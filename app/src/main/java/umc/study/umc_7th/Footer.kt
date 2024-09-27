package umc.study.umc_7th

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class SocialContact(
    val url: String,
    val iconId: Int,
) {
    FACEBOOK(url = "", iconId = R.drawable.ic_main_facebook),
    INSTAGRAM(url = "", iconId = R.drawable.ic_main_instagram),
    YOUTUBE(url = "", iconId = R.drawable.ic_main_youtube),
    TWITTER(url = "", iconId = R.drawable.ic_main_twitter),
}

val companyName = "(주)드림어스컴퍼니"

@Composable
fun Footer() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(
                space = 32.dp,
                alignment = Alignment.CenterHorizontally
            ),
        ) {
            SocialContact.entries.forEach { socialContact ->
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        painter = painterResource(id = socialContact.iconId),
                        contentDescription = null,
                    )
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { /*TODO*/ }
        ) {
            Text(
                text = AnnotatedString("$companyName 사업자 정보"),
                color = Color.Black.copy(alpha = 0.5f),
            )
            Icon(
                painter = painterResource(id = R.drawable.btn_arrow_more),
                contentDescription = null,
                tint = Color.Black.copy(alpha = 0.5f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFooter() {
    Footer()
}