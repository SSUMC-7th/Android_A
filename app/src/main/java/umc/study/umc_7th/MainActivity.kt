package umc.study.umc_7th

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.study.umc_7th.ui.composables.BottomNavigationBar
import umc.study.umc_7th.data.model.Content
import umc.study.umc_7th.ui.composables.NavigationDestination
import umc.study.umc_7th.ui.composables.Footer
import umc.study.umc_7th.ui.composables.GlobeCategorizedMusicCollectionView
import umc.study.umc_7th.ui.composables.GlobeCategory
import umc.study.umc_7th.ui.composables.MainBanner
import umc.study.umc_7th.ui.composables.MiniPlayer
import umc.study.umc_7th.ui.composables.PodcastCollectionView
import umc.study.umc_7th.ui.composables.PromotionImageBanner
import umc.study.umc_7th.ui.composables.VideoCollectionView
import umc.study.umc_7th.ui.screen.AlbumFragment
import umc.study.umc_7th.ui.screen.LockerFragment
import umc.study.umc_7th.ui.screen.LookFragment
import umc.study.umc_7th.ui.theme.Umc_7thTheme
import umc.study.umc_7th.ui.viewmodel.MockMusicViewModel
import umc.study.umc_7th.ui.viewmodel.MusicViewModel
import java.time.LocalDate

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Toast 메시지를 띄워주는 함수
    fun Context.showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this@MainActivity, message, duration).show()
    }

    private lateinit var musicViewModel: MusicViewModel
    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        musicViewModel = ViewModelProvider(this)[MusicViewModel::class.java]
        musicViewModel.insertDummyAlbums()
        musicViewModel.insertDummySongs()
        sharedPreferences = getSharedPreferences("song_pref", Context.MODE_PRIVATE)
        val songId = sharedPreferences.getInt("id", 0)

        enableEdgeToEdge()
        setContent {
            Umc_7thTheme {
                MyApp(viewModel = musicViewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val songId = intent.getIntExtra("song_id", -1)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp(viewModel: MusicViewModel) {
    // 네비게이션 컨트롤러 생성
    val navController = rememberNavController()
    var currentDestination by remember { mutableStateOf(NavigationDestination.HOME) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                currentDestination = currentDestination,
                onDestinationClicked = { destination ->
                    currentDestination = destination
                    navController.navigate(destination.expression)
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = NavigationDestination.HOME.expression,
            Modifier.padding(innerPadding)
        ) {
            composable(NavigationDestination.HOME.expression) { HomeScreen(navController, viewModel = viewModel) }
            composable(NavigationDestination.LOOK.expression) { LookScreen() }
            composable(NavigationDestination.SEARCH.expression) { }
            composable(NavigationDestination.MY.expression) { LockerScreen(viewModel = viewModel) }
            composable("Album") { AlbumScreen(viewModel = viewModel) }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(navController: NavController, viewModel: MusicViewModel) {
    val currentTime by viewModel.currentTime.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column {
                MiniPlayer(viewModel = viewModel, progress = currentTime)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(count = 1) { item ->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(385.dp)
                ) {
                    MainBanner(
                        title = "포근하게 덮어주는 꿈의 목소리",
                        date = LocalDate.parse("2019-11-11"),
                        contentList = List(15) {
                            Content(
                                title = "Butter",
                                singer = "BTS",
                                image = ImageBitmap.imageResource(id = R.drawable.img_album_exp),
                                length = 200,
                            )
                        },
                        textColor = Color.White,
                        backgroundImage = ImageBitmap.imageResource(id = R.drawable.img_default_4_x_1),
                        onVoiceSearchButtonClicked = { /*TODO*/ },
                        onSubscriptionButtonClicked = { /*TODO*/ },
                        onSettingButtonClicked = { /*TODO*/ }) {
                    }
                }
                GlobeCategorizedMusicCollectionView(
                    title = "오늘 발매 음악",
                    viewModel = MockMusicViewModel(),
                    globeCategory = GlobeCategory.GLOBAL,
                    onViewTitleClicked = { /*TODO*/ },
                    onContentClicked = { navController.navigate("Album") },
                    onCategoryClicked = { /*TODO*/ },
                )
                PromotionImageBanner(image = ImageBitmap.imageResource(id = R.drawable.img_home_viewpager_exp),
                    onClicked = {}
                )
                PodcastCollectionView(title = "매일 들어도 좋은 팟캐스트",
                    contentList = List(15) {
                        Content(
                            title = "김시선의 귀책사유 FLO X 윌라",
                            singer = "김시선",
                            image = ImageBitmap.imageResource(id = R.drawable.img_potcast_exp),
                            length = 200,
                        )
                    },
                    onContentClicked = {})
                VideoCollectionView(title = "비디오 콜렉션",
                    contentList = List(15) {
                        Content(
                            title = "제목",
                            singer = "지은이",
                            image = ImageBitmap.imageResource(id = R.drawable.img_video_exp),
                            length = 200,
                        )
                    },
                    onContentClicked = {})
                PromotionImageBanner(image = ImageBitmap.imageResource(id = R.drawable.discovery_banner_aos),
                    onClicked = {})
                Spacer(modifier = Modifier.height(30.dp))
                PromotionImageBanner(image = ImageBitmap.imageResource(id = R.drawable.img_home_viewpager_exp2),
                    onClicked = {})
                Spacer(modifier = Modifier.height(20.dp))
                Footer()
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AlbumScreen(viewModel: MusicViewModel) {
    AlbumFragment(viewModel = viewModel)
}

@Composable
fun LockerScreen(viewModel: MusicViewModel) {
    LockerFragment(viewModel = viewModel)
}

@Composable
fun LookScreen() {
    LookFragment()
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MyAppPreview(widthDp: Dp = 412.dp, heightDp: Dp = 915.dp) {
    Umc_7thTheme {
        val mockViewModel = MockMusicViewModel()
        MyApp(viewModel = mockViewModel)
    }
}
