package umc.study.umc_7th.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import umc.study.umc_7th.Album
import umc.study.umc_7th.AlbumContent
import umc.study.umc_7th.Content
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.VideoContent
import umc.study.umc_7th.data.AuthRepository
import umc.study.umc_7th.data.ContentRepository
import umc.study.umc_7th.service.ServiceHandler
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val authRepository: AuthRepository,
    private val serviceHandler: ServiceHandler,
) : ViewModel() {
    val bannerContents = MutableStateFlow<List<List<MusicContent>>>(emptyList())
    val albums = MutableStateFlow<List<Album>>(emptyList())
    val podcasts = MutableStateFlow<List<PodcastContent>>(emptyList())
    val videos = MutableStateFlow<List<VideoContent>>(emptyList())

    val likedContents = MutableStateFlow<List<Content>>(emptyList())

    val savedMusics get() = contentRepository.getAllSavedMusicsFlow()
    val savedAlbums get() = contentRepository.getAllSavedAlbumsFlow()

    val isServiceConnected get() = serviceHandler.isServiceConnected
    val isPlaying get() = serviceHandler.isPlaying
    val playingPoint get() = serviceHandler.playingPoint
    val currentContent get() = serviceHandler.currentContent

    init {
        viewModelScope.launch {
            launch {
                contentRepository.getAllLikeLogsFlow()
                    .collect { _ -> loadLikedContents(refresh = false) }
            }
        }
    }

    fun initialize() {
        serviceHandler.bindService()
        loadRecommendedContents()
        loadLikedContents(refresh = true)
    }

    fun getAlbum(id: Long, onSuccess: (AlbumContent) -> Unit, onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                val album = contentRepository.getAlbum(id)
                onSuccess(album)
            } catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }

    fun setLike(id: Long, like: Boolean, onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                contentRepository.setLike(id, like)
                loadLikedContents(refresh = false)
            } catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }

    fun deleteMusic(music: MusicContent, onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                contentRepository.deleteMusic(music)
            } catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }

    fun setCurrentContent(content: Content) = serviceHandler.play(content)
    fun setPlaylist(playlist: List<MusicContent>) = serviceHandler.setPlaylist(playlist)
    fun playNext() = serviceHandler.next()
    fun playPrevious() = serviceHandler.previous()
    fun append(music: MusicContent) = serviceHandler.append(music)

    fun setPlay(play: Boolean) {
        if (play) serviceHandler.resume()
        else serviceHandler.pause()
    }

    fun logout(
        onSuccess: () -> Unit,
        onFailed: () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                authRepository.logout()
                serviceHandler.unbindService()
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }

    fun isAlbumSaved(
        album: AlbumContent,
        onSuccess: (Boolean) -> Unit,
        onFailed: () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                val savedAlbum = contentRepository.getSavedAlbum(album.id)
                onSuccess(savedAlbum != null)
            } catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }

    fun saveAlbum(
        album: AlbumContent,
        save: Boolean,
        onSuccess: () -> Unit,
        onFailed: () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                if (save) contentRepository.saveAlbum(album)
                else contentRepository.deleteAlbum(album)
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }

    private fun loadRecommendedContents() {
        bannerContents.value = emptyList()
        albums.value = emptyList()
        podcasts.value = emptyList()
        videos.value = emptyList()

        val routines = listOf<suspend () -> Unit>(
            { albums.value = contentRepository.getRandomAlbums(10) },
            { podcasts.value = contentRepository.getRandomPodcasts(10) },
            { videos.value = contentRepository.getRandomVideos(10) },
        ) + List(5) {
            {
                val musics = contentRepository.getRandomMusics(10)
                bannerContents.value = bannerContents.value.plus(listOf(musics))
            }
        }

        routines.forEach { routine ->
            viewModelScope.launch {
                try {
                    routine()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun loadLikedContents(refresh: Boolean) {
        viewModelScope.launch {
            val newLikedContents = mutableListOf<Content>()
            try {
                contentRepository.getAllLikeLogs(refresh = refresh).toList()
                    .sortedByDescending { it.second }
                    .forEach { (id, _) ->
                        try {
                            val content = contentRepository.getMusic(id)
                            newLikedContents.add(content)
                        } catch (_: Exception) {
                            // TODO: 다른 유형의 콘텐츠에도 적용 가능하도록 수정
                        }
                    }
                likedContents.value = newLikedContents.toList()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
