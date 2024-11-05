package umc.study.umc_7th

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MusicViewModel : ViewModel() {

    var song_title = mutableStateOf("")
    var song_author = mutableStateOf("")

    private val _currentTime = MutableStateFlow(0f)
    val currentTime : StateFlow<Float> = _currentTime

    private var _isPlaying = MutableStateFlow(false) // 재생 상태 관리
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val handler = Handler(Looper.getMainLooper())
    private var updateRunnable: Runnable? = null

    init {
        startProgress()
    }

    private fun startProgress() {
        updateRunnable = object : Runnable {
            override fun run() {
                if (_isPlaying.value) {
                    _currentTime.value += 1f / 60f
                    if (_currentTime.value >= 1f) _currentTime.value = 0f // 최대치 도달 시 0으로 초기화
                }
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(updateRunnable!!)
    }

    fun play(title : String, author : String) {
        song_title.value = title
        song_author.value = author
    }

    fun togglePlayPause() {
        _isPlaying.value = !_isPlaying.value // 재생/일시정지 전환
    }

    fun updateCurrentTime(newTime: Float) {
        _currentTime.value = newTime / 60f
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(updateRunnable!!)
    }
}