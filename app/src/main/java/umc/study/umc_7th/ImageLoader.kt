package umc.study.umc_7th

import androidx.compose.ui.graphics.ImageBitmap
import umc.study.umc_7th.network.Server

object ImageLoader {
    private val imageMap = emptyMap<Long, ImageBitmap>().toMutableMap()

    suspend fun getImageBitmap(
        id: Long,
        refresh: Boolean = false,
    ): ImageBitmap {
        val image = imageMap[id]
        if (refresh || image == null) {
            val loadedImage = Server.getImage(id)
            imageMap[id] = loadedImage
            return loadedImage
        }
        else return image
    }
}