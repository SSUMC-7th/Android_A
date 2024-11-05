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
    private val _bannerContents = mutableStateListOf<List<MusicContent>>()
    private val _albums = mutableStateListOf<Album>()
    private val _podcasts = mutableStateListOf<PodcastContent>()
    private val _videos = mutableStateListOf<VideoContent>()
    private val _savedMusics = mutableStateListOf<MusicContent>()

    private val _currentAlbum = mutableStateOf<AlbumContent?>(null)

    val bannerContents get() = _bannerContents.toList()
    val albums get() = _albums.toList()
    val podcasts get() = _podcasts.toList()
    val videos get() = _videos.toList()
    val savedMusics get() = _savedMusics.toList()

    var currentAlbum: AlbumContent? get() = _currentAlbum.value
        private set(value) { _currentAlbum.value = value }

    init {
        viewModelScope.launch {
            try {
                repeat(5) { _bannerContents.add(Server.getRandomMusics((5..10).random())) }
                _albums.addAll(Server.getRandomAlbums(10))
                _podcasts.addAll(Server.getRandomPodcasts(10))
                _videos.addAll(Server.getRandomVideos(10))
                _savedMusics.addAll(Server.getRandomMusics(50))
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

    fun deleteSavedMusic(music: MusicContent) {
        _savedMusics.remove(music)
    }
}
