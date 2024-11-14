package umc.study.umc_7th

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.launch
import umc.study.umc_7th.data.network.Server
import umc.study.umc_7th.di.ImageLoaderEntryPoint
import javax.inject.Inject

class ImageLoader @Inject constructor(
    private val server: Server,
) {
    private val imageMap = emptyMap<Long, ImageBitmap>().toMutableMap()

    suspend fun getImageBitmap(
        id: Long,
        refresh: Boolean = false,
    ): ImageBitmap {
        val image = imageMap[id]
        if (refresh || image == null) {
            val loadedImage = server.getImage(id)
            imageMap[id] = loadedImage
            return loadedImage
        }
        return image
    }
}

@Composable
fun SuspendedImage(
    id: Long,
    content: @Composable (ImageBitmap?) -> Unit,
) {
    if (id < 0) {
        content(
            ImageBitmap.imageResource(
                id = when (id) {
                    -1L -> R.drawable.img_album_exp2
                    -2L -> R.drawable.img_potcast_exp
                    -3L -> R.drawable.img_video_exp
                    else -> throw IllegalArgumentException("wrong preview image id")
                }
            )
        )
        return
    }

    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    val scope = rememberCoroutineScope()

    val imageLoader = EntryPointAccessors
        .fromApplication<ImageLoaderEntryPoint>(LocalContext.current)
        .getImageLoader()

    LaunchedEffect(key1 = id) {
        scope.launch {
            image = imageLoader.getImageBitmap(id)
        }
    }

    content(image)
}