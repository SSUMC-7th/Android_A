package umc.study.umc_7th

import android.content.Intent
import android.os.Bundle
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.FragmentActivity
import umc.study.umc_7th.home.HomeFragment
import umc.study.umc_7th.song.SongActivity

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerView_main, homeFragment)
            .commit()

        val composeViewMain = findViewById<ComposeView>(R.id.composeView_main)
        composeViewMain.setContent {
            BottomNavigationBar(
                currentDestination = NavigationDestination.HOME,
                currentContent = Content(
                    title = "Butter",
                    author = "BTS",
                    imageId = R.drawable.img_album_exp,
                    length = 200,
                ),
                isPlaying = false,
                onDestinationClicked = { /*TODO*/ },
                onContentClicked = { content ->
                    val intent = Intent(this, SongActivity::class.java)
                    intent.putExtra("content", content)
                    startActivity(intent)
                },
                onPlayButtonClicked = { /*TODO*/ },
                onNextButtonClicked = { /*TODO*/ },
                onPreviousButtonClicked = { /*TODO*/ }) {
            }
        }
    }
}
