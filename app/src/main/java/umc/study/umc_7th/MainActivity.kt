package umc.study.umc_7th

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import umc.study.umc_7th.ui.theme.Umc_7thTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Umc_7thTheme {
                MyApp()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp() {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Column() {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    MiniPlayer()
                }
                BottomNavigationBar(onDestinationClicked = { /*TODO*/ },)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(count = 1) { item ->
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
                    onSettingButtonClicked = { /*TODO*/ },
                    onPlayButtonClicked = { /*TODO*/ })
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
                    onContentClicked = { /*TODO*/ },
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
@Preview
@Composable
fun MyAppPreview(widthDp: Dp = 412.dp, heightDp: Dp = 915.dp) {
    Umc_7thTheme {
        MyApp()
    }
}
