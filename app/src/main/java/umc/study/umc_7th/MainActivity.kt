package umc.study.umc_7th

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import umc.study.umc_7th.ui.theme.Umc_7thTheme
import androidx.navigation.compose.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import java.time.LocalDate


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            Umc_7thTheme {
                Column {
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
                    MiniPlayer(
                        content = Content(
                            title = "butter",
                            author = "bts",
                            image = ImageBitmap.imageResource(id = R.drawable.img_album_exp2),
                            length = 200
                        ),
                        beforeSongPlayButtonClick = { /*TODO*/ },
                        playSongButtonClick = { /*TODO*/ },
                        nextSongPlayButtonClick = { /*TODO*/ },
                        toSongActivity = { content ->
                            val intent = Intent(this@MainActivity, SongActivity::class.java).apply{
                                putExtra("songtitle", content.title)
                                putExtra("author", content.author)
                            }
                            startActivity(intent)
                        },
                        musicQueueClick = { /*TODO*/ }
                    )
                    BottomNavigationBar(
                        onClick = {},
                    )
                }

            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun homeFragment(navController: NavController){
    Column {
        MainBanner(
            title1 = "포근하게 덮어주는 꿈의",
            title2 = "목소리",
            date = LocalDate.parse("2019-11-11"),
            contentList = List(15){
                Content(
                    title = "Butter",
                    author = "BTS",
                    image = ImageBitmap.imageResource(id = R.drawable.img_album_exp),
                    length = 200,
                )
            },
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
    }

}



