package umc.study.umc_7th

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import umc.study.umc_7th.ui.theme.Umc_7thTheme
import androidx.navigation.compose.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.time.LocalDate


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = (application as MyApplication).songViewModel
        enableEdgeToEdge()
        setContent {
            Umc_7thTheme {
                Scaffold(
                    bottomBar = {
                        Column (modifier = Modifier.padding(0.dp)){
                            MainMiniplayer(
                                viewModel = viewModel,
                                content = Content(
                                title = "butter",
                                author = "bts",
                                image = ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
                                length = 200
                            ),toSongActivity = { content ->
                                val intent = Intent(this@MainActivity, SongActivity::class.java).apply{
                                    putExtra("songtitle", content.title)
                                    putExtra("author", content.author)
                                }
                                startActivity(intent)
                            } )
                            BottomNavigationBar(
                                onClick = {},
                            )
                        }

                    }
                ) { innerPadding ->
                    Box(modifier  = Modifier
                        .padding(innerPadding)
                        ) {

                    }
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "homeFragment") {

                        composable("homeFragment") { homeFragment(navController) }
                        composable(
                            "albumFragment/{albumTitle}/{author}/{date}/{trackList}/{titleTrackList}",
                            arguments = listOf(
                                navArgument("albumTitle") { type = NavType.StringType },
                                navArgument("author") { type = NavType.StringType },
                                navArgument("date") { type = NavType.StringType },
                                navArgument("trackList") { type = NavType.StringType },
                                navArgument("titleTrackList") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val albumTitle = backStackEntry.arguments?.getString("albumTitle") ?: ""
                            val author = backStackEntry.arguments?.getString("author") ?: ""
                            val date = backStackEntry.arguments?.getString("date") ?: ""
                            val trackList = backStackEntry.arguments?.getString("trackList")?.split(",") ?: listOf()
                            val titleTrackList = backStackEntry.arguments?.getString("titleTrackList")?.split(",") ?: listOf()
                            albumFragment(navController, albumTitle, author, LocalDate.parse(date), trackList, titleTrackList)
                        }
                    }

                }

            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun homeFragment(navController: NavController){
    Column (
        modifier = Modifier.verticalScroll(rememberScrollState())
    ){
        MainBanner(
            title1 = "포근하게 덮어주는 꿈의",
            title2 = "목소리",
            date = LocalDate.parse("2019-11-11"),
            contentList = listOf(
                Content(
                    title = "Butter",
                    author = "BTS",
                    image = ImageBitmap.imageResource(id = R.drawable.img_album_exp),
                    length = 200,
                ),Content(
                    title = "LILAC",
                    author = "아이유(IU)",
                    image = ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
                    length = 200,
                )
                ),
            backgroundImage = ImageBitmap.imageResource(id = R.drawable.img_default_4_x_1) ,
            textColor =Color.White ,
            MikeButtonClick = { /*TODO*/ },
            TicketButtonClick = { /*TODO*/ },
            SettingButtonClick = { /*TODO*/ }) {}

        LocationMusicContentView(
            title = "오늘 발매 음악",
            contentList = List(15){
                Album(
                    albumTitle = "IU 5th Album 'LILAC'",
                    date = LocalDate.parse("2023-03-27"),
                    author = "IU(아이유)",
                    albumImage = ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
                    trackList = listOf("LILAC", "Coin", "Flu", "Troll", "Lovesick"),
                    titleTrackList = listOf("LILAC", "Flu")
                )
            },
            baseLocationCategory = BaseLocationCategory.GLOABAL ,
            viewTitleClick = { },
            contentClick ={ album ->
                navController.navigate("albumFragment/${album.albumTitle}/${album.author}/${album.date}/${album.trackList.joinToString(",")}/${album.titleTrackList.joinToString(",")}")},
            categoryClick = {}
        )
        PodcastCollectionView(
            title = "매일 들어도 좋은 팟캐스트" ,
            contentList = List(15){
                Content(
                    title = "김시선의 귀책사유 FLO X 윌라",
                    author= "김시선",
                    image = ImageBitmap.imageResource(id = R.drawable.img_potcast_exp),
                    length = 200,
                )
            },
            contentClick = {}
        )
        VideoCollectionView(
            title ="비디오 콜렉션" ,
            contentList =List(15){
                Content(
                    title = "제목",
                    author = "지은이",
                    image = ImageBitmap.imageResource(id = R.drawable.img_video_exp),
                    length = 200,
                )
            } ,
            contentClick = {}
        )
    }

}

@Composable
@RequiresApi(Build.VERSION_CODES.P)
fun MainMiniplayer(viewModel : SongViewModel ,
    content : Content, toSongActivity : (Content) -> Unit){
    MiniPlayer(
        viewModel = viewModel,
        content = content,
        beforeSongPlayButtonClick = { /*TODO*/ },
        playSongButtonClick = { /*TODO*/ },
        nextSongPlayButtonClick = { /*TODO*/ },
        toSongActivity = toSongActivity,
        musicQueueClick = { /*TODO*/ }
    )
}


