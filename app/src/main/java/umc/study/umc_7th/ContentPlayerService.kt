package umc.study.umc_7th

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContentPlayerService: Service() {
    companion object {
        private const val CHANNEL_ID = "content_player"
        private const val CHANNEL_NAME = "Content Player"
        private const val NOTIFICATION_ID = 1
    }

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

        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("FLO")
            .setContentText("원하는 음악을 재생하세요.")
            .setSmallIcon(R.mipmap.ic_launcher_round)

        startForeground(NOTIFICATION_ID, notification.build())

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

                setNotification(
                    title = "지금 재생 중",
                    text = "${content.author} - ${content.title}",
                )
            }
        }
    }

    fun getContent(): Content? {
        return _currentContent.value
    }

    fun pause() {
        mediaPlayer?.let {
            it.pause()
            _isPlaying.value = false
        }

        _currentContent.value?.let { content ->
            setNotification(
                title = "일시 정지됨",
                text = "${content.author} - ${content.title}",
            )
        }
    }

    fun resume() {
        mediaPlayer?.let{
            it.start()
            _isPlaying.value = true
        }

        _currentContent.value?.let { content ->
            setNotification(
                title = "지금 재생 중",
                text = "${content.author} - ${content.title}",
            )
        }
    }

    fun seek(position: Int /* 단위: 초 */) {
        mediaPlayer?.seekTo(position * 1000)
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        timer.cancel()
        super.onDestroy()
    }

    private fun setNotification(
        title: String,
        text: String,
    ) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder = NotificationCompat.Builder(this@ContentPlayerService, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.mipmap.ic_launcher_round)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}