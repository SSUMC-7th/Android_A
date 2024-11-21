package umc.study.umc_7th.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.flow.asStateFlow
import umc.study.umc_7th.Content
import umc.study.umc_7th.MusicContent

class ServiceHandler(private val context: Context) {
    private lateinit var contentPlayerService: ContentPlayerService
    var isServiceConnected: Boolean = false
        private set

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as ContentPlayerService.LocalBinder
            contentPlayerService = binder.getService()
            isServiceConnected = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isServiceConnected = false
        }

        override fun onBindingDied(name: ComponentName?) {
            isServiceConnected = false
        }
    }

    fun bindService() {
        context.apply {
            val intent = Intent(this, ContentPlayerService::class.java)
            startForegroundService(intent)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    fun unbindService() {
        isServiceConnected = false
        context.apply {
            val intent = Intent(this, ContentPlayerService::class.java)
            unbindService(connection)
            stopService(intent)
        }
    }

    val isPlaying get() = contentPlayerService.isPlaying.asStateFlow()
    val playingPoint get() = contentPlayerService.playingPoint.asStateFlow()
    val currentContent get() = contentPlayerService.currentContent.asStateFlow()
    val playlist get() = contentPlayerService.playlist.asStateFlow()
    val playingIndex get() = contentPlayerService.playingIndex.asStateFlow()

    fun play(content: Content) = contentPlayerService.play(content)
    fun setPlaylist(playlist: List<MusicContent>) = contentPlayerService.setPlaylist(playlist)
    fun resume() = contentPlayerService.resume()
    fun pause() = contentPlayerService.pause()
    fun seek(position: Int) = contentPlayerService.seek(position)
    fun append(content: MusicContent) = contentPlayerService.append(content)
    fun next() = contentPlayerService.next()
    fun previous() = contentPlayerService.previous()
}