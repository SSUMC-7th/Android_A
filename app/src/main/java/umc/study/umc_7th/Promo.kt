package umc.study.umc_7th

import android.os.Build
import android.provider.ContactsContract.CommonDataKinds.Im
import androidx.annotation.RequiresApi
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

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Promotion(
    image : ImageBitmap,
    padding: Dp = 0.dp,
    onClicked: ()-> Unit
) {
    Box(modifier = Modifier
    )
    {
        Image(
            bitmap = image,
            contentDescription =null,
            modifier = Modifier.clickable { onClicked() }
        )
    }
}
@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewPromotion(){
    Promotion(image = ImageBitmap.imageResource(id = R.drawable.img_home_viewpager_exp2),
        onClicked = {},)
}