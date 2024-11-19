package umc.study.umc_7th.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import umc.study.umc_7th.MusicContent
import umc.study.umc_7th.databinding.ActivityMainBinding
import umc.study.umc_7th.ui.song.SongActivity
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition {
            if (viewModel.isServiceConnected) {
                binding.composeViewMain.setContent { Screen() }
                return@setKeepOnScreenCondition false
            } else return@setKeepOnScreenCondition true
        }

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.initialize()
        navigate(NavigationDestination.HOME)
    }

    @Composable
    private fun Screen() {
        var currentDestination by remember { mutableStateOf(NavigationDestination.HOME) }

        val currentContent by viewModel.currentContent.collectAsStateWithLifecycle()
        val isPlaying by viewModel.isPlaying.collectAsStateWithLifecycle()
        val playingPoint by viewModel.playingPoint.collectAsStateWithLifecycle()

        BottomNavigationBar(
            currentDestination = currentDestination,
            currentContent = currentContent,
            playingPoint = playingPoint,
            isPlaying = isPlaying,
            onDestinationClicked = {
                navigate(it)
                currentDestination = it
            },
            onContentClicked = lambda@{ content ->
                Intent(
                    this@MainActivity,
                    when (content.javaClass) {
                        MusicContent::class.java -> SongActivity::class.java
                        else -> return@lambda  // TODO: 다른 콘텐츠 타입에 대한 내비게이팅 구현
                    },
                ).also {
                    startActivity(it)
                }
            },
            onPlayButtonClicked = { viewModel.setPlay(it) },
            onNextButtonClicked = { viewModel.playNext() },
            onPreviousButtonClicked = { viewModel.playPrevious() },
            onPlaylistButtonClicked = { /* TODO */ },
        )
    }

    private fun navigate(destination: NavigationDestination) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerViewMain.id, destination.getFragment()).commit()
    }
}
