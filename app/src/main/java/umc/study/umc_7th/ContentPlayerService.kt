package umc.study.umc_7th

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContentPlayerService: Service() {
    private var mediaPlayer: MediaPlayer? = null

    private val _currentContent = MutableStateFlow<Content?>(null)
    private val _currentPlayingPoint = MutableStateFlow(0)
    private val _isPlaying = MutableStateFlow(false)

    val currentContent: StateFlow<Content?> = _currentContent.asStateFlow()
    val currentPlayingPoint: StateFlow<Int> = _currentPlayingPoint.asStateFlow()
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val binder = LocalBinder()
    inner class LocalBinder: Binder() {
        fun getService(): ContentPlayerService = this@ContentPlayerService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    fun setContent(content: Content, startPoint: Int = 0  /* 단위: 초 */) {
        // val url = "${BuildConfig.SERVER_URL}/stream/sound/${content.id}"
        val url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"

        _currentContent.value = null
        mediaPlayer?.release()

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { mp ->
                mp.seekTo(startPoint * 1000)
                mp.start()
                _currentContent.value = content
                _isPlaying.value = true
            }
        }
    }

    fun pause() {
        mediaPlayer?.let {
            it.pause()
            _isPlaying.value = false
        }
    }

    fun resume() {
        mediaPlayer?.let{
            it.start()
            _isPlaying.value = true
        }
    }

    fun seek(position: Int /* 단위: 초 */) {
        mediaPlayer?.seekTo(position * 1000)
    }

    fun getPlayingTime(): Int? /* 단위: 초 */ {
        return mediaPlayer?.currentPosition?.div(1000)
    }

    fun getDuration(): Int? /* 단위: 초 */ {
        return mediaPlayer?.duration?.div(1000)
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}