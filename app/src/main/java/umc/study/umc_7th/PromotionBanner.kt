package umc.study.umc_7th

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PromotionImageBanner(
    image: ImageBitmap,
    padding: Dp = 0.dp,
    onClicked: () -> Unit,
) {
    Box(modifier = Modifier.padding(padding)) {
        Image(
            bitmap = image,
            contentDescription = null,
            modifier = Modifier.clickable { onClicked() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPromotionImageBanner() {
    PromotionImageBanner(
        image = ImageBitmap.imageResource(id = R.drawable.img_home_viewpager_exp),
        onClicked = {},
    )
}