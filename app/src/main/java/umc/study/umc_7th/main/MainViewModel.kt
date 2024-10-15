package umc.study.umc_7th.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import umc.study.umc_7th.Album
import umc.study.umc_7th.AlbumContent
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.VideoContent
import umc.study.umc_7th.network.Server

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel: ViewModel() {
    private val _currentAlbum = mutableStateOf<AlbumContent?>(null)

    val bannerContents = mutableStateListOf<List<MusicContent>>()
    val albums = mutableStateListOf<Album>()
    val podcasts = mutableStateListOf<PodcastContent>()
    val videos = mutableStateListOf<VideoContent>()
    var currentAlbum: AlbumContent? get() = _currentAlbum.value
        private set(value) { _currentAlbum.value = value }

    init {
        viewModelScope.launch {
            try {
                repeat(5) { bannerContents.add(Server.getRandomMusics((5..10).random())) }
                albums.addAll(Server.getRandomAlbums(10))
                podcasts.addAll(Server.getRandomPodcasts(10))
                videos.addAll(Server.getRandomVideos(10))
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
            }
            catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }
}
