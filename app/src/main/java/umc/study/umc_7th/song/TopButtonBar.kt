package umc.study.umc_7th.song

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
    onSettingButtonClicked: () -> Unit,
    onEqualizerButtonClicked: () -> Unit,
    onMinimizeButtonClicked: () -> Unit,
    onDetailsButtonClicked: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onSettingButtonClicked,
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_player_setting),
                    contentDescription = null,
                )
            }
            IconButton(
                onClick = onEqualizerButtonClicked,
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_player_eq_off),
                    contentDescription = null,
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(
                onClick = onMinimizeButtonClicked,
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.nugu_btn_down),
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
        onSettingButtonClicked = {},
        onDetailsButtonClicked = {},
        onEqualizerButtonClicked = {},
        onMinimizeButtonClicked = {},
    )
}