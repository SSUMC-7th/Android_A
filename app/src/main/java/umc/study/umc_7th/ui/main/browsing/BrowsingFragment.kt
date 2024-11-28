package umc.study.umc_7th.ui.main.browsing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import umc.study.umc_7th.databinding.FragmentBrowsingBinding
import umc.study.umc_7th.previewMusicContentList
import umc.study.umc_7th.ui.main.MainViewModel
import umc.study.umc_7th.ui.theme.Umc_7thTheme
import java.time.LocalDateTime
import kotlin.random.Random

@AndroidEntryPoint
class BrowsingFragment: Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentBrowsingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        binding = FragmentBrowsingBinding.inflate(layoutInflater)

        binding.composeViewBrowsing.setContent {
            Umc_7thTheme {
                Scaffold { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Screen()
                    }
                }
            }
        }

        return binding.root
    }

    @Composable
    private fun Screen() {
        BrowsingScreen()
    }
}

@Composable
private fun BrowsingScreen() {
    val tabs = remember {
        listOf(
            TabItem(
                label = "차트",
                page = {
                    MusicChartPage(
                        chart = previewMusicContentList.map { it to Random.nextInt(-10, 10) },
                        baseTime = LocalDateTime.now(),
                        onTitleClicked = { /* TODO */ },
                        onPlayAllButtonClicked = { /* TODO */ },
                        onPlayContentClicked = { /* TODO */ },
                        onContentDetailsClicked = { /* TODO */ },
                    )
                }
            ),
        ) + listOf("영상", "장르", "상황", "분위기", "오디오").map {
            TabItem(
                label = it,
                page = { Text(text = "${it}가 없습니다.") },
            )
        }
    }

    Column {
        TitleBar(title = "둘러보기")
        TabLayout(tabs = tabs)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewBrowsingScreen() {
    Umc_7thTheme {
        Scaffold { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                BrowsingScreen()
            }
        }
    }
}