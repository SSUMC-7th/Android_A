package umc.study.umc_7th

import android.os.Bundle
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.imageResource
import androidx.fragment.app.FragmentActivity
import umc.study.umc_7th.Home.HomeFragment

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
                    title = "Peanut butter Sandwich",
                    author = "jusokuryClub",
                    image = ImageBitmap.imageResource(id = R.drawable.img_album_exp),
                    length = 200,
                ),
                isPlaying = false,
                onDestinationClicked = { /*TODO*/ },
                onContentClicked = { /*TODO*/ },
                onPlayButtonClicked = { /*TODO*/ },
                onNextButtonClicked = { /*TODO*/ },
                onPreviousButtonClicked = { /*TODO*/ }) {
            }
        }
    }
}