package umc.study.umc_7th.locker

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import umc.study.umc_7th.BottomNavigationBar
import umc.study.umc_7th.NavigationDestination
import umc.study.umc_7th.R
import umc.study.umc_7th.album.TabItem
import umc.study.umc_7th.getTestMusicContentList

class LockerFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false).apply {
            findViewById<ComposeView>(R.id.composeView_home).setContent {
                LockerScreen()
            }
        }
    }
}

@Composable
fun LockerScreen() {
    Column {
        TitleBar(
            title = "보관함",
            isLoginDone = false,
            onLoginClicked = {}
        )
        TabLayout(
            tabs = listOf(
                TabItem(
                    label = "저장한 곡",
                    page = { SavedMusicPage() }
                ),
                TabItem(
                    label = "음악파일",
                    page = { StorageFilePage() }
                )
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLockerScreen() {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onPlaylistButtonClicked = {},
                onDestinationClicked = {},
                onPreviousButtonClicked = {},
                onNextButtonClicked = {},
                onPlayButtonClicked = {},
                onContentClicked = {},
                currentDestination = NavigationDestination.HOME,
                currentContent = getTestMusicContentList((1..4).random()).random(),
                isPlaying = false,
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LockerScreen()
        }
    }
}