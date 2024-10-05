package umc.study.umc_7th.song

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import umc.study.umc_7th.R

@Composable
fun FooterActionBar(
    onInstagramButtonClicked: () -> Unit,
    onSimilarSongButtonClicked: () -> Unit,
    onPlaylistButtonClicked: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
    ) {
        IconButton(
            onClick = onInstagramButtonClicked,
            modifier = Modifier.size(32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_actionbar_instagram),
                contentDescription = null,
            )
        }
        IconButton(
            onClick = onSimilarSongButtonClicked,
            modifier = Modifier.size(32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_player_related),
                contentDescription = null,
            )
        }
        IconButton(
            onClick = onPlaylistButtonClicked,
            modifier = Modifier.size(32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_player_go_list),
                contentDescription = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFooterActionBar() {
    FooterActionBar(
        onPlaylistButtonClicked = {},
        onInstagramButtonClicked = {},
        onSimilarSongButtonClicked = {},
    )
}