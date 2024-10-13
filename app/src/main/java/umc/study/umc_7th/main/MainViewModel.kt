package umc.study.umc_7th.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import umc.study.umc_7th.Album
import umc.study.umc_7th.ImageLoader
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.VideoContent
import umc.study.umc_7th.network.Server

class MainViewModel: ViewModel() {
    private val _currentAlbum = mutableStateOf<Album?>(null)

    val bannerContents = mutableStateListOf<List<MusicContent>>()
    val musics = mutableStateListOf<MusicContent>()
    val podcasts = mutableStateListOf<PodcastContent>()
    val videos = mutableStateListOf<VideoContent>()
    var currentAlbum: Album? get() = _currentAlbum.value
        private set(value) { _currentAlbum.value = value }

    init {
        viewModelScope.launch {
            try {
                repeat(5) { bannerContents.add(Server.getRandomMusics((5..10).random())) }
                musics.addAll(Server.getRandomMusics(10))
                podcasts.addAll(Server.getRandomPodcasts(10))
                videos.addAll(Server.getRandomVideos(10))

                bannerContents.forEachIndexed { index, contents ->
                    bannerContents[index] = contents.map { content ->
                        content.copy(image = ImageLoader.getImageBitmap(content.imageId))
                    }
                }

                musics.forEachIndexed { index, content ->
                    val image = ImageLoader.getImageBitmap(
                        id = content.imageId,
                        refresh = false,
                    )
                    musics[index] = content.copy(image = image)
                }

                podcasts.forEachIndexed { index, content ->
                    val image = ImageLoader.getImageBitmap(
                        id = content.imageId,
                        refresh = false,
                    )
                    podcasts[index] = content.copy(image = image)
                }

                videos.forEachIndexed { index, content ->
                    val image = ImageLoader.getImageBitmap(
                        id = content.imageId,
                        refresh = false,
                    )
                    videos[index] = content.copy(image = image)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAlbum(id: Long, onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                val album = Server.getAlbum(id)
                currentAlbum = album
                val image = ImageLoader.getImageBitmap(id = album.imageId)
                currentAlbum = album.copy(image = image)
            }
            catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }
}
