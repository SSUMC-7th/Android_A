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
import umc.study.umc_7th.VideoContent
import umc.study.umc_7th.data.ContentRepository
import umc.study.umc_7th.service.ServiceHandler
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val serviceHandler: ServiceHandler,
): ViewModel() {
    private val _bannerContents = mutableStateListOf<List<MusicContent>>()
    private val _albums = mutableStateListOf<Album>()
    private val _podcasts = mutableStateListOf<PodcastContent>()
    private val _videos = mutableStateListOf<VideoContent>()

    private val _savedMusics = mutableStateOf<List<MusicContent>>(emptyList())
    private val _likedContents = mutableStateOf<List<Content>>(emptyList())

    val isServiceConnected get() = serviceHandler.isServiceConnected

    val bannerContents get() = _bannerContents.toList()
    val albums get() = _albums.toList()
    val podcasts get() = _podcasts.toList()
    val videos get() = _videos.toList()

    val savedMusics get() = _savedMusics.value
    val likedContents get() = _likedContents.value

    val isPlaying get() = serviceHandler.isPlaying
    val playingPoint get() = serviceHandler.playingPoint
    val currentContent get() = serviceHandler.currentContent

    init {
        viewModelScope.launch {
            listOf<suspend () -> Unit>(
                {
                    repeat(5) {
                        val rand = (0..9).random()
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
                    contentRepository.getAllLikedContentsFlow().collect { likes ->
                        val newLikedContent = mutableListOf<Content>()
                        likes.sortedByDescending { it.second }.forEach {
                            // TODO: 음악 콘텐츠 의외의 유형에도 작동하도록 할 것
                            try {
                                contentRepository.getMusic(it.first)
                                    .let { music -> newLikedContent.add(music) }
                            }
                            catch (_: Exception) {}
                        }
                        _likedContents.value = newLikedContent.toList()
                    }
                },
            ).forEach {
                launch {
                    try{
                        it()
                    }
                    catch(e: Exception) {
                        e.printStackTrace()
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
            }
            catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }

    fun setLike(id: Long, like: Boolean, onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                if (like) contentRepository.like(id)
                else contentRepository.unlike(id)
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

    fun play(content: Content) = serviceHandler.play(content)
    fun setPlaylist(playlist: List<MusicContent>) = serviceHandler.setPlaylist(playlist)
    fun playNext() = serviceHandler.next()
    fun playPrevious() = serviceHandler.previous()
    fun append(music: MusicContent) = serviceHandler.append(music)

    fun setPlay(play: Boolean) {
        if (play) serviceHandler.resume()
        else serviceHandler.pause()
    }
}
