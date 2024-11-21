package umc.study.umc_7th.ui.song

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.data.ContentRepository
import umc.study.umc_7th.service.ServiceHandler
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val serviceHandler: ServiceHandler,
) : ViewModel() {
    val isLiked = MutableStateFlow(false)

    val isPlaying get() = serviceHandler.isPlaying
    val playingPoint get() = serviceHandler.playingPoint
    val currentContent get() = serviceHandler.currentContent

    init {
        viewModelScope.launch {
            launch {
                currentContent.collect { content ->
                    isLiked.value = content != null && try {
                        contentRepository.isLiked(content.id)
                    } catch (e: Exception) {
                        false
                    }
                }
            }
        }
    }

    fun setLike(like: Boolean, onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                currentContent.value?.let { content ->
                    contentRepository.setLike(content.id, like)
                    isLiked.value = like
                    Log.d("SongViewModel", "setLike: $like")
                }
            } catch (e: Exception) {
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
            } catch (e: Exception) {
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