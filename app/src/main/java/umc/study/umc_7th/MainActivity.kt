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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.pager.HorizontalPager

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.time.LocalDate
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.yield

import androidx.compose.foundation.layout.navigationBarsPadding


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = (application as MyApplication).songViewModel
        enableEdgeToEdge()
        setContent {
            Umc_7thTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.navigationBarsPadding(),
                    bottomBar = {
                        Column (modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ){
                            MainMiniplayer(
                                viewModel = viewModel,
                                content = Content(
                                title = "butter",
                                author = "bts",
                                image = R.drawable.img_album_exp2,
                                length = 200
                            ),toSongActivity = { content ->
                                val intent = Intent(this@MainActivity, SongActivity::class.java).apply{
                                    putExtra("songtitle", content.title)
                                    putExtra("author", content.author)
                                }
                                startActivity(intent)
                            } )
                            BottomNavigationBar(
                                navController ,
                                onClick = {destination -> navController.navigate(destination.route)},
                            )
                        }

                    }
                ) { innerPadding ->
                    Box(modifier  = Modifier
                        .padding(innerPadding)
                        ) {
                        NavHost(navController = navController, startDestination = "homeFragment") {

                            composable("homeFragment") { homeFragment(navController) }
                            composable(
                                "albumFragment/{albumTitle}/{albumImage}/{author}/{date}/{trackList}/{titleTrackList}",
                                arguments = listOf(
                                    navArgument("albumTitle") { type = NavType.StringType },
                                    navArgument("albumImage"){ type = NavType.StringType },
                                    navArgument("author") { type = NavType.StringType },
                                    navArgument("date") { type = NavType.StringType },
                                    navArgument("trackList") { type = NavType.StringType },
                                    navArgument("titleTrackList") { type = NavType.StringType }
                                )
                            ) { backStackEntry ->
                                val albumTitle = backStackEntry.arguments?.getString("albumTitle") ?: ""
                                val albumImageString= backStackEntry.arguments?.getString("albumImage")?:"0"
                                val albumImage = albumImageString.toIntOrNull()?:0
                                val author = backStackEntry.arguments?.getString("author") ?: ""
                                val date = backStackEntry.arguments?.getString("date") ?: ""
                                val trackList = backStackEntry.arguments?.getString("trackList")?.split(",") ?: listOf()
                                val titleTrackList = backStackEntry.arguments?.getString("titleTrackList")?.split(",") ?: listOf()
                                albumFragment(navController, albumTitle, albumImage, author, LocalDate.parse(date), trackList, titleTrackList)
                            }
//                            composable(
//                                "albumFragment",
//                                arguments = listOf(navArgument("album") { type = NavType.ParcelableType(Album::class.java) })
//                            ) { backStackEntry ->
//                                val album = backStackEntry.arguments?.getParcelable<Album>("album")
//                                album?.let {
//                                    albumFragment(navController)
//                                }
//                            }
                            composable("lockerFragment") { LockerFragment(navController) }
                            composable("searchFragment") { searchFragment(navController) }
                            composable("aroundFragment") { aroundFragment(navController) }
                        }
                    }



                }

            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun homeFragment(navController: NavController){
    Column (
        modifier = Modifier.verticalScroll(rememberScrollState())
    ){
        val pagerState = rememberPagerState()
        LaunchedEffect(pagerState) {
            while (isActive){
                yield()
                delay(3000L)
                val nextPage = (pagerState.currentPage + 1) % (bannerDataList.size)
                pagerState.animateScrollToPage(nextPage)
            }
        }
        HorizontalPager(
            bannerDataList.size,
            state = pagerState,
            modifier = Modifier.fillMaxWidth()){
            page ->
            val banner = bannerDataList[page]
            MainBanner(
                title1 = banner.title1,
                date = banner.date,
                contentList = banner.contentList,
                backgroundImage = ImageBitmap.imageResource(id = banner.backgroundImage),
                textColor = Color(banner.textColor),
                MikeButtonClick = { /*TODO*/ },
                TicketButtonClick = { /*TODO*/ },
                SettingButtonClick = { /*TODO*/ }) {

            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            activeColor = Color.Blue,
            inactiveColor = Color.Gray)

        val albumList = albumData
        LocationMusicContentView(
            title = "오늘 발매 음악",
            contentList = albumList,
            baseLocationCategory = BaseLocationCategory.GLOABAL ,
            viewTitleClick = { },
            contentClick ={ album ->
                navController.navigate("albumFragment/${album.albumTitle}/${album.albumImage}/${album.author}/${album.date}/${album.trackList.joinToString(",")}/${album.titleTrackList.joinToString(",")}")},
//                navController.navigate("albumFragment")},
            categoryClick = {}
        )
        PodcastCollectionView(
            title = "매일 들어도 좋은 팟캐스트" ,
            contentList = List(15){
                Content(
                    title = "김시선의 귀책사유 FLO X 윌라",
                    author= "김시선",
                    image = R.drawable.img_potcast_exp,
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
                    image = R.drawable.img_video_exp,
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


