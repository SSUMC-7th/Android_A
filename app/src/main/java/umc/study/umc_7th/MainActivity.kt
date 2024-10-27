package umc.study.umc_7th

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import umc.study.umc_7th.ui.theme.Umc_7thTheme
import java.time.LocalDate

var songTitle : String? = null
var songAuthor : String? = null

class MainActivity : ComponentActivity() {

    // Toast 메시지를 띄워주는 함수
    fun Context.showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this@MainActivity, message, duration).show()
    }

    private val viewModel: MusicViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Umc_7thTheme {
                MyApp(viewModel = viewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        songTitle = intent.getStringExtra("songTitle")
        songAuthor = intent.getStringExtra("songAuthor")
        if (songTitle != null && songAuthor != null) {
            showToast(songTitle)
            showToast(songAuthor)
        }
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
            composable(NavigationDestination.LOOK.expression) {  }
            composable(NavigationDestination.SEARCH.expression) { }
            composable(NavigationDestination.MY.expression) { LockerScreen() }
            composable("Album") { AlbumScreen() }
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
                MiniPlayer(viewModel = viewModel, progress = currentTime, songTitle, songAuthor)
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
                                author = "BTS",
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
                    contentList = List(15) {
                        Content(
                            title = "LILAC",
                            author = "IU",
                            image = ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
                            length = 200,
                        )
                    },
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
                            author = "김시선",
                            image = ImageBitmap.imageResource(id = R.drawable.img_potcast_exp),
                            length = 200,
                        )
                    },
                    onContentClicked = {})
                VideoCollectionView(title = "비디오 콜렉션",
                    contentList = List(15) {
                        Content(
                            title = "제목",
                            author = "지은이",
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
fun AlbumScreen() {
    AlbumFragment()
}

@Composable
fun LockerScreen() {
    LockerFragment()
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MyAppPreview(widthDp: Dp = 412.dp, heightDp: Dp = 915.dp) {
    Umc_7thTheme {
        MyApp(viewModel = MusicViewModel())
    }
}
