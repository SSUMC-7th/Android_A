package umc.study.umc_7th.main.song

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import umc.study.umc_7th.R

@Composable
fun SongTopButton(
    SettingButtonClick: () -> Unit,
    eqButtonClick: () -> Unit,
    songActivityToMainActivity: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        IconButton(onClick = SettingButtonClick,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.btn_player_setting),
                contentDescription = null,

                )
        }
        IconButton(onClick = eqButtonClick,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.btn_player_eq_off),
                contentDescription = null,
                modifier = Modifier
                )
            }
        }
        IconButton(onClick = songActivityToMainActivity,
            modifier = Modifier.size(36.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.nugu_btn_down),
                contentDescription = null,
            )
        }
    }
}