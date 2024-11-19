package umc.study.umc_7th.ui.main

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.study.umc_7th.Album
import umc.study.umc_7th.AlbumContent
import umc.study.umc_7th.Content
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.PodcastContent
import umc.study.umc_7th.User
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
    private val _bannerContents = mutableStateListOf<List<MusicContent>>()
    private val _albums = mutableStateListOf<Album>()
    private val _podcasts = mutableStateListOf<PodcastContent>()
    private val _videos = mutableStateListOf<VideoContent>()

    private val _user = mutableStateOf<User?>(null)

    private val _savedMusics = mutableStateOf<List<MusicContent>>(emptyList())
    private val _savedAlbums = mutableStateOf<List<AlbumContent>>(emptyList())
    private val _likedContents = mutableStateOf<List<Content>>(emptyList())

    val isServiceConnected get() = serviceHandler.isServiceConnected

    val bannerContents get() = _bannerContents.toList()
    val albums get() = _albums.toList()
    val podcasts get() = _podcasts.toList()
    val videos get() = _videos.toList()

    val user get() = _user.value

    val savedMusics get() = _savedMusics.value
    val savedAlbums get() = _savedAlbums.value
    val likedContents get() = _likedContents.value

    val isPlaying get() = serviceHandler.isPlaying
    val playingPoint get() = serviceHandler.playingPoint
    val currentContent get() = serviceHandler.currentContent

    fun initialize() {
        _bannerContents.clear()
        _albums.clear()
        _podcasts.clear()
        _videos.clear()

        _user.value = null
        _savedMusics.value = emptyList()
        _likedContents.value = emptyList()

        viewModelScope.launch {
            Log.d("debug", "started")
            listOf<suspend () -> Unit>(
                { _user.value = authRepository.getMyProfile() },
                {
                    repeat(5) {
                        val rand = (5..15).random()
                        _bannerContents.add(contentRepository.getRandomMusics(rand))
                    }
                },
                { _albums.addAll(contentRepository.getRandomAlbums(10)) },
                { _podcasts.addAll(contentRepository.getRandomPodcasts(10)) },
                { _videos.addAll(contentRepository.getRandomVideos(10)) },
                {
                    contentRepository.getAllSavedMusicsFlow()
                        .collect { musics -> _savedMusics.value = musics }
                },
                {
                    contentRepository.getAllSavedAlbumsFlow()
                        .collect { albums -> _savedAlbums.value = albums }
                },
                {
                    val newLikedContents = mutableListOf<Content>()
                    contentRepository.getAllLikeLog().sortedByDescending { it.second }
                        .map { (id, _) ->
                            try {
                                val content = contentRepository.getMusic(id)
                                newLikedContents.add(content)
                            } catch (_: Exception) {
                                // TODO: 다른 유형의 콘텐츠에도 적용 가능하도록 수정
                            }
                        }
                    _likedContents.value = newLikedContents.toList()
                },
            ).forEachIndexed { index, callback ->
                launch {
                    try {
                        callback()
                        Log.d("debug", "$index ended")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.d("debug", "$index failed")
                    }
                }
            }
        }
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

    fun play(content: Content) = serviceHandler.play(content)
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
                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }
}
