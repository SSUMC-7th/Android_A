package umc.study.umc_7th

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContentPlayerService: Service() {
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var timer: Job

    private val _currentContent = MutableStateFlow<Content?>(null)
    private val _isPlaying = MutableStateFlow(false)
    private val _playingPoint = MutableStateFlow<Int?>(0)

    val currentContent: StateFlow<Content?> = _currentContent.asStateFlow()
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()
    val playingPoint: StateFlow<Int?> = _playingPoint.asStateFlow()

    private val binder = LocalBinder()
    inner class LocalBinder: Binder() {
        fun getService(): ContentPlayerService = this@ContentPlayerService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()

        timer = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                _playingPoint.value = mediaPlayer?.currentPosition?.div(1000)
                delay(100)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    fun setContent(content: Content, startPoint: Int = 0  /* 단위: 초 */) {
        val url = "${BuildConfig.SERVER_URL}/stream/sound/${content.id}"

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

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}