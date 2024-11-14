package umc.study.umc_7th.ui.song

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.data.ContentRepository
import umc.study.umc_7th.service.ServiceHandler
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val serviceHandler: ServiceHandler,
): ViewModel() {
    private val _isLiked = mutableStateOf(false)
    val isLiked get() = _isLiked.value

    val isPlaying get() = serviceHandler.isPlaying
    val playingPoint get() = serviceHandler.playingPoint
    val currentContent get() = serviceHandler.currentContent

    init {
        viewModelScope.launch {
            currentContent.collect { content ->
                _isLiked.value = content != null && contentRepository.isLiked(content.id)
            }
        }
    }

    fun setLike(like: Boolean, onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                currentContent.value?.let { content ->
                    if (like) contentRepository.like(content.id)
                    else contentRepository.unlike(content.id)
                    _isLiked.value = like
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }

    fun save(onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                currentContent.value?.let { content ->
                    if (content is MusicContent)
                        contentRepository.saveMusic(content)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }

    fun playNext() = serviceHandler.next()
    fun playPrevious() = serviceHandler.previous()
    fun seek(position: Int) = serviceHandler.seek(position)

    fun setPlay(play: Boolean) {
        if (play) serviceHandler.resume()
        else serviceHandler.pause()
    }
}