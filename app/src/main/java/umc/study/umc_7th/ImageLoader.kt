package umc.study.umc_7th

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource

object ImageLoader {
    private var idSequence = 0
        get() = --field
    private val imageMap = emptyMap<Int, ImageBitmap>().toMutableMap()

    @Composable
    fun getImageBitmap(id: Int): ImageBitmap {
        if (!imageMap.containsKey(id) && id > 0)
            imageMap[id] = ImageBitmap.imageResource(id = id)
        return imageMap[id]!!
    }

    fun putImageBitmap(image: ImageBitmap): Int {
        val id = idSequence
        imageMap[id] = image
        return id
    }
}