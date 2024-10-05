package umc.study.umc_7th

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import java.io.Serializable

data class Content(
    val title: String,
    val author: String,
    val imageId: Int,
    val length: Int,  // 단위: 초
): Serializable {
    val imageBitmap: ImageBitmap @Composable get() = ImageLoader.getImageBitmap(id = imageId)
}

@Composable
fun getTestContentList(): List<Content> = listOf(
    "라일락",
    "Flu",
    "Coin",
    "봄 안녕 봄",
    "Celebrity",
    "돌림노래 (feat. DEAN)",
    "빈 컵 (Empty Cup)",
    "아이와 나의 바다",
    "어푸 (Ah puh)",
    "에필로그",
).map {
    Content(
        title = it,
        author = "아이유(IU)",
        imageId = R.drawable.img_album_exp2,
        length = 210,
    )
}