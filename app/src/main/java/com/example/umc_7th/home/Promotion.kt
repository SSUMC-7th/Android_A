package com.example.umc_7th.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.umc_7th.R

@Composable
fun Promotion(
    image: Painter,
    padding: Dp = 0.dp,
    onClicked: () -> Unit
) {
    Box(modifier = Modifier.padding(padding)) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.clickable { onClicked() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPromotion() {
    val image = painterResource(id = R.drawable.img_home_viewpager_exp2)
    Promotion(
        image = image,
        padding = 8.dp,
        onClicked = {}
    )
}

