package umc.study.umc_7th.ui.song

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.study.umc_7th.Content
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.data.ContentRepository
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
): ViewModel() {
    private val _content = mutableStateOf<Content?>(null)
    private val _isLiked = mutableStateOf(false)

    val content get() = _content.value
    val isLiked get() = _isLiked.value

    fun getMusic(id: Long, onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                val music = contentRepository.getMusic(id)
                _content.value = music
                _isLiked.value = contentRepository.isLiked(music.id)
            }
            catch (e: Exception) {
                e.printStackTrace()
                onFailed()
            }
        }
    }

    fun setLike(like: Boolean, onFailed: () -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("SongViewModel", "setLike: $like, id: ${_content.value?.id}")
                content?.let { content ->
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
                content?.let { content ->
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
}