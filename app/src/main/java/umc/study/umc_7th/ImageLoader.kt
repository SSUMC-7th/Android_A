package umc.study.umc_7th

import androidx.compose.ui.graphics.ImageBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import umc.study.umc_7th.network.Server

object ImageLoader {
    private val imageMap = emptyMap<Long, ImageBitmap>().toMutableMap()

    fun getImageBitmap(
        scope: CoroutineScope,
        id: Long,
        onLoaded: (ImageBitmap) -> Unit,
        refresh: Boolean = false,
    ) {
        val image = imageMap[id]
        if (refresh || image == null) scope.launch {
            val loadedImage = Server.getImage(id)
            imageMap[id] = loadedImage
            onLoaded(loadedImage)
        }
        else onLoaded(image)
    }
}