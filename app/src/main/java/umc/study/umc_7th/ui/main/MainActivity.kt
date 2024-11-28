package umc.study.umc_7th.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
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

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition {
            if (viewModel.isServiceConnected) {
                navigate(NavigationDestination.HOME)
                binding.composeViewMain.setContent { NavigationBar() }
                return@setKeepOnScreenCondition false
            } else return@setKeepOnScreenCondition true
        }

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.initialize()
    }

    @Composable
    private fun NavigationBar() {
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
                val intent = Intent(
                    this@MainActivity,
                    when (content.javaClass) {
                        MusicContent::class.java -> SongActivity::class.java
                        else -> return@lambda  // TODO: 다른 콘텐츠 타입에 대한 내비게이팅 구현
                    },
                )

                startActivity(intent)
            },
            onPlayButtonClicked = { viewModel.setPlay(it) },
            onNextButtonClicked = { viewModel.playNext() },
            onPreviousButtonClicked = { viewModel.playPrevious() },
            onPlaylistButtonClicked = { /* TODO */ },
        )
    }

    private fun navigate(destination: NavigationDestination) {
        val fragment = destination.getFragment()
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerViewMain.id, fragment).commit()
    }
}
