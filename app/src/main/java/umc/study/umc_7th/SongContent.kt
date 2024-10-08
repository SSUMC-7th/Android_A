package umc.study.umc_7th

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TopButtonView(){
    val context = LocalContext.current

    Row() {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.btn_player_setting)
                contentDescription = "setting"
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(onClick = {/*TODO*/}) {
            Icon(
                painter = painterResource(id = R.drawable.btn_player_eq_off),
                contentDescription = "eq"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column() {
            IconButton(onClick = {
                val intent = Intent(context, MainActivity::class.java).apply {
                    putExtra("songTitle", "라일락")
                    putExtra("songAuthor", "아이유 (IU)")
                }
                    context.startActivity(intent)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.nugu_btn_down)
                    contentDescription = "backPress"
                )
            }
        }
        }
    }