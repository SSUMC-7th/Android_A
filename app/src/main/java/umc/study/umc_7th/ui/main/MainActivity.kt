package umc.study.umc_7th.ui.main

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import umc.study.umc_7th.ContentPlayerService
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.R
import umc.study.umc_7th.ui.song.SongActivity
import java.util.concurrent.CountDownLatch

class MainActivity : FragmentActivity() {
    private val viewModel: MainViewModel by viewModels()
    lateinit var contentPlayerService: ContentPlayerService
    private val serviceBound = CountDownLatch(1)

    private val connection = object: ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as ContentPlayerService.LocalBinder
            contentPlayerService = binder.getService()
            serviceBound.countDown()
        }

        override fun onServiceDisconnected(arg0: ComponentName) = Unit
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Intent(this, ContentPlayerService::class.java).also {
            startForegroundService(it)
            bindService(it, connection, Context.BIND_AUTO_CREATE)
        }

        lifecycleScope.launch(Dispatchers.IO) {
            serviceBound.await()
            setScreen()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connection)
    }

    private fun setScreen() {
        navigate(NavigationDestination.HOME)

        val composeView = findViewById<ComposeView>(R.id.composeView_main)
        composeView.setContent {
            var currentDestination by remember { mutableStateOf(NavigationDestination.HOME) }
            val currentContent by contentPlayerService.currentContent.collectAsStateWithLifecycle()
            val isPlaying by contentPlayerService.isPlaying.collectAsStateWithLifecycle()
            val playingPoint by contentPlayerService.playingPoint.collectAsStateWithLifecycle()

            BottomNavigationBar(
                currentDestination = currentDestination,
                currentContent = currentContent,
                playingPoint = playingPoint,
                isPlaying = isPlaying,
                onDestinationClicked = {
                    navigate(it)
                    currentDestination = it
                },
                onContentClicked = lambda@ { content ->
                    Intent(
                        this@MainActivity,
                        when (content.javaClass) {
                            MusicContent::class.java -> SongActivity::class.java
                            else -> return@lambda  // TODO: 다른 콘텐츠 타입에 대한 내비게이팅 구현
                        },
                    ).apply {
                        putExtra("content_id", content.id)
                    }.also {
                        startActivity(it)
                    }
                },
                onPlayButtonClicked = {
                    if (contentPlayerService.isPlaying.value)
                        contentPlayerService.pause()
                    else
                        contentPlayerService.resume()
                },
                onNextButtonClicked = { /* TODO */ },
                onPreviousButtonClicked = { /* TODO */ },
                onPlaylistButtonClicked = { /* TODO */ },
            )
        }
    }

    private fun navigate(destination: NavigationDestination) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainerView_main, destination.fragment)
            .commit()
    }
}
