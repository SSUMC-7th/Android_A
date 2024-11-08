package umc.study.umc_7th.ui.song

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    onDetailsClicked: () -> Unit,
    onSaveClicked: () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

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
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.btn_player_more),
                    contentDescription = null,
                )
                DropdownMenu(
                    onDismissRequest = { isExpanded = false },
                    expanded = isExpanded,
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "정보") },
                        onClick = onDetailsClicked,
                    )
                    DropdownMenuItem(
                        text = { Text(text = "저장") },
                        onClick = onSaveClicked,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopButtonBar() {
    TopButtonBar(
        onSettingButtonClicked = {},
        onDetailsClicked = {},
        onEqualizerButtonClicked = {},
        onMinimizeButtonClicked = {},
        onSaveClicked = {},
    )
}
