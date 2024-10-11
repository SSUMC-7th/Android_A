package umc.study.umc_7th.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.FragmentActivity
import umc.study.umc_7th.R
import umc.study.umc_7th.main.home.HomeFragment
import umc.study.umc_7th.main.locker.LockerFragment
import umc.study.umc_7th.song.SongActivity

class MainActivity : FragmentActivity() {
    private val viewModel: MainViewModel = MainViewModel()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerView_main, HomeFragment())
            .commit()

        val composeViewMain = findViewById<ComposeView>(R.id.composeView_main)
        composeViewMain.setContent {
            val currentContent = viewModel.musics.firstOrNull()
            var currentDestination by remember { mutableStateOf(NavigationDestination.HOME) }

            BottomNavigationBar(
                currentDestination = currentDestination,
                currentContent = currentContent,
                isPlaying = false,
                onDestinationClicked = {
                    when (it) {
                        NavigationDestination.HOME -> run {
                            currentDestination = NavigationDestination.HOME
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView_main, HomeFragment())
                                .commit()
                        }

                        NavigationDestination.MY -> run {
                            currentDestination = NavigationDestination.MY
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerView_main, LockerFragment())
                                .commit()
                        }

                        else -> Unit
                    }
                },
                onContentClicked = { content ->
                    val intent = Intent(this, SongActivity::class.java)
                    intent.putExtra("contentId", content.id)
                    intent.putExtra("contentType", content.javaClass.name)
                    startActivity(intent)
                },
                onPlayButtonClicked = { /*TODO*/ },
                onNextButtonClicked = { /*TODO*/ },
                onPreviousButtonClicked = { /*TODO*/ }) {
            }
        }
    }
}
