package umc.study.umc_7th.data.model

import androidx.compose.ui.graphics.ImageBitmap

data class Content(
    val title: String,
    val singer: String,
    val image: ImageBitmap,
    val length: Int,  // 단위: 초
)