package umc.study.umc_7th

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

@Composable
fun TopButtonBar (
    onSettingButtonClicked: () -> Unit,
    onEqualizerButtonClicked: () -> Unit,
    onMinimizeButtonClicked: () -> Unit,
    onDetailsButtonCliked: () -> Unit,
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
            //Setting Button
            IconButton(
                onClick = onSettingButtonClicked,
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_player_setting),
                        contentDescription = null,
                )
            }
            //EQ Button
            IconButton (
                onClick = onEqualizerButtonClicked,
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_player_eq_off),
                    contentDescription = null,
                )
            }
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            //nugu
            IconButton(
                onClick = onMinimizeButtonClicked,
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.nugu_btn_down),
                    contentDescription = null,
                )
            }
            IconButton(
                onClick = onDetailsButtonCliked,
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.btn_player_more),
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
        onDetailsButtonCliked =  {},
        onEqualizerButtonClicked = {},
        onMinimizeButtonClicked = {},
    )
}