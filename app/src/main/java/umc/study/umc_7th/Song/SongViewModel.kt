package umc.study.umc_7th.Song

import com.android.volley.toolbox.ImageLoader

package umc.study.umc_7th.song

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import umc.study.umc_7th.Content
import umc.study.umc_7th.ImageLoader
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.VideoContent
import umc.study.umc_7th.network.Server

class SongViewModel: ViewModel() {
    val content = mutableStateOf<Content?>(null)

    fun getContent(
        id: Long,
        type: Class<*>,
        onFailed: () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                when (type) {
                    MusicContent::class.java -> {
                        val loadedContent = Server.getMusic(id)!!
                        content.value = loadedContent
                        val image = ImageLoader.getImageBitmap(id = loadedContent.imageId)
                        content.value = loadedContent.copy(image = image)
                    }
                    PodcastContent::class.java -> {
                        val loadedContent = Server.getPodcast(id)
                        content.value = loadedContent
                        val image = ImageLoader.getImageBitmap(id = loadedContent.imageId)
                        content.value = loadedContent.copy(image = image)
                    }
                    VideoContent::class.java -> {
                        val loadedContent = Server.getVideo(id)
                        content.value = loadedContent
                        val image = ImageLoader.getImageBitmap(id = loadedContent.imageId)
                        content.value = loadedContent.copy(image = image)
                    }
                    else -> throw IllegalArgumentException("Invalid type")
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }
}