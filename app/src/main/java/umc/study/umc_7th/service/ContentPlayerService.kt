package umc.study.umc_7th.service

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
import kotlinx.coroutines.launch
import umc.study.umc_7th.BuildConfig
import umc.study.umc_7th.Content
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.R

class ContentPlayerService : Service() {
    companion object {
        private const val CHANNEL_ID = "content_player"
        private const val CHANNEL_NAME = "Content Player"
        private const val NOTIFICATION_ID = 1
    }

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var timer: Job

    val currentContent = MutableStateFlow<Content?>(null)
    val playlist = MutableStateFlow<List<MusicContent>>(emptyList())
    val playingIndex = MutableStateFlow(0)
    val isPlaying = MutableStateFlow(false)
    val playingPoint = MutableStateFlow<Int?>(0)

    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): ContentPlayerService = this@ContentPlayerService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()

        val channel =
            NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("FLO")
            .setContentText("원하는 음악을 재생하세요.").setSmallIcon(R.mipmap.ic_launcher_round)

        startForeground(NOTIFICATION_ID, notification.build())

        timer = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                playingPoint.value = mediaPlayer?.currentPosition?.div(1000)
                delay(100)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        timer.cancel()
        super.onDestroy()
    }

    fun play(content: Content) {
        mediaPlayer?.release()
        currentContent.value = content
        isPlaying.value = false
        playingPoint.value = 0

        val url = "${BuildConfig.SERVER_URL}/stream/sound/${content.id}"

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA).build()
            )
            setDataSource(url)
            prepareAsync()
            setOnPreparedListener { mp ->
                mp.start()
                this@ContentPlayerService.isPlaying.value = true

                setNotification(
                    title = "지금 재생 중",
                    text = "${content.author} - ${content.title}",
                )
            }
            setOnCompletionListener {
                if (!next()) {
                    this@ContentPlayerService.isPlaying.value = false
                    playingPoint.value = 0
                }
            }
        }
    }

    fun setPlaylist(playlist: List<MusicContent>) {
        this.playlist.value = playlist
        playingIndex.value = 0
        play(playlist[0])
    }

    fun pause() {
        mediaPlayer?.let {
            it.pause()
            isPlaying.value = false
        }

        currentContent.value?.let { content ->
            setNotification(
                title = "일시 정지됨",
                text = "${content.author} - ${content.title}",
            )
        }
    }

    fun resume() {
        mediaPlayer?.let {
            it.start()
            isPlaying.value = true
        }

        currentContent.value?.let { content ->
            setNotification(
                title = "지금 재생 중",
                text = "${content.author} - ${content.title}",
            )
        }
    }

    fun seek(position: Int) {
        mediaPlayer?.seekTo(position * 1000)
    }

    fun append(music: MusicContent) {
        playlist.value = playlist.value.plus(music)
    }

    fun next(): Boolean {
        val index = playingIndex.value
        val list = playlist.value
        if (index + 1 == list.size) return false
        play(list[index + 1])
        playingIndex.value = index + 1
        return true
    }

    fun previous(): Boolean {
        val index = playingIndex.value
        val list = playlist.value
        if (index == 0) return false
        play(list[index - 1])
        playingIndex.value = index - 1
        return true
    }

    private fun setNotification(
        title: String,
        text: String,
    ) {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder =
            NotificationCompat.Builder(this@ContentPlayerService, CHANNEL_ID).setContentTitle(title)
                .setContentText(text).setSmallIcon(R.mipmap.ic_launcher_round)
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }
}