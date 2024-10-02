package umc.study.umc_7th

import androidx.compose.ui.graphics.ImageBitmap

data class Content(
    val title: String,
    val author: String,
    val image: ImageBitmap,
    val length: Int,  // 단위: 초
)