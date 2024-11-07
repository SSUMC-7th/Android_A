package umc.study.umc_7th.ui.main.album

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import umc.study.umc_7th.R

@Composable
fun TopButtonBar(
    isLiked: Boolean,
    onBackButtonClicked: () -> Unit,
    onLikeButtonClicked: (Boolean) -> Unit,
    onDetailsButtonClicked: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        IconButton(
            onClick = onBackButtonClicked,
            modifier = Modifier.size(32.dp),
        ) {
            Icon(
                painter = painterResource(id = R.drawable.btn_arrow_black),
                contentDescription = null,
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = { onLikeButtonClicked(!isLiked) },
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isLiked) R.drawable.ic_my_like_on
                        else R.drawable.ic_my_like_off
                    ),
                    contentDescription = null,
                )
            }
            IconButton(
                onClick = onDetailsButtonClicked,
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_player_more),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopButtonBar() {
    TopButtonBar(
        isLiked = false,
        onBackButtonClicked = {},
        onLikeButtonClicked = {},
        onDetailsButtonClicked = {}
    )
}