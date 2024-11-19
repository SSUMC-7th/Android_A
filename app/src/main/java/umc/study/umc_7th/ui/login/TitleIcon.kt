package umc.study.umc_7th.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import umc.study.umc_7th.R

@Composable
fun TitleIcon() {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_flo_logo),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(0.3f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTitleIcon() {
    TitleIcon()
}