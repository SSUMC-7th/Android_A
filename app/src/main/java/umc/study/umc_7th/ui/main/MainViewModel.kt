package umc.study.umc_7th.ui.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.study.umc_7th.Album
import umc.study.umc_7th.AlbumContent
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.VideoContent
import umc.study.umc_7th.data.ContentRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
): ViewModel() {
    private val _bannerContents = mutableStateListOf<List<MusicContent>>()
    private val _albums = mutableStateListOf<Album>()
    private val _podcasts = mutableStateListOf<PodcastContent>()
    private val _videos = mutableStateListOf<VideoContent>()
    private val _savedMusics = mutableStateOf<List<MusicContent>>(emptyList())

    private val _currentAlbum = mutableStateOf<AlbumContent?>(null)

    val bannerContents get() = _bannerContents.toList()
    val albums get() = _albums.toList()
    val podcasts get() = _podcasts.toList()
    val videos get() = _videos.toList()
    val savedMusics get() = _savedMusics.value

    var currentAlbum: AlbumContent? get() = _currentAlbum.value
        private set(value) { _currentAlbum.value = value }

    init {
        viewModelScope.launch {
            try {
                repeat(5) { _bannerContents.add(contentRepository.getRandomMusics((5..10).random())) }
                _albums.addAll(contentRepository.getRandomAlbums(10))
                _podcasts.addAll(contentRepository.getRandomPodcasts(10))
                _videos.addAll(contentRepository.getRandomVideos(10))

                contentRepository.getAllSavedMusicFlow().collect { musics -> _savedMusics.value = musics }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getAlbum(id: Long, onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                val album = contentRepository.getAlbum(id)
                currentAlbum = album
            }
            catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }

    fun deleteMusic(music: MusicContent, onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                contentRepository.deleteMusic(music)
            }
            catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }
}
