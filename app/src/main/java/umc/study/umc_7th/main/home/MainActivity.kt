package umc.study.umc_7th.main.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.pager.HorizontalPager

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import umc.study.umc_7th.ui.theme.Umc_7thTheme
import androidx.navigation.compose.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import umc.study.umc_7th.content.Content
import umc.study.umc_7th.MyApplication
import umc.study.umc_7th.R
import umc.study.umc_7th.SongViewModel
import umc.study.umc_7th.album.albumFragment
import umc.study.umc_7th.content.albumData
import umc.study.umc_7th.aroundFragment
import umc.study.umc_7th.content.AppDataBase
import umc.study.umc_7th.content.ContentRepository
import umc.study.umc_7th.content.bannerDataList
import umc.study.umc_7th.locker.LockerFragment
import umc.study.umc_7th.main.song.MiniPlayer
import umc.study.umc_7th.main.song.SongActivity
import umc.study.umc_7th.searchFragment
import umc.study.umc_7th.startMusicService


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = (application as MyApplication).songViewModel


        enableEdgeToEdge()
        setContent {
            Umc_7thTheme {
                val navController = rememberNavController()

                var showLockerBottomBar by remember{ mutableStateOf(false)}
                Scaffold(
                    modifier = Modifier.navigationBarsPadding(),
                    bottomBar = {
                        if(showLockerBottomBar){
                            LockerBottomBar()
                        }else{
                            homeBottomNavigation(viewModel = viewModel, navController = navController)
                        }

                    }
                ) { innerPadding ->
                    Box(modifier  = Modifier
                        .padding(innerPadding)
                        ) {
                        NavHost(navController = navController, startDestination = "homeFragment") {

                            composable("homeFragment") { homeFragment(navController, viewModel) }
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
                            composable("lockerFragment") { LockerFragment(viewModel,
                                showBottomBar = { showLockerBottomBar = true},
                                hideBottomBar = { showLockerBottomBar = false})
                            }
                            composable("searchFragment") { searchFragment(navController) }
                            composable("aroundFragment") { aroundFragment(navController) }
                        }
                    }



                }

            }
        }
        startMusicService(this)
    }
}



@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun homeFragment(navController: NavController,
                 viewModel: SongViewModel
){
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
                navController.navigate("albumFragment/${album.albumTitle}/${album.albumImage}/${album.author}" +
                        "/${album.date}/${album.trackList.joinToString(",")}/${album.titleTrackList.joinToString(",")}")},
            categoryClick = {},
            albumMusicStart = {album ->
                // 첫 번째 트랙을 `currentSong`으로 설정
                val firstTrackContent = Content(
                    title = album.trackList.firstOrNull() ?: "Unknown Track",
                    author = album.author,
                    image = album.albumImage,
                    length = 200,
                    islike = false
                )
                viewModel.setCurrentSong(firstTrackContent)

            },
            viewModel = viewModel
        )
        PodcastCollectionView(
            title = "매일 들어도 좋은 팟캐스트" ,
            contentList = List(15){
                Content(
                    title = "김시선의 귀책사유 FLO X 윌라",
                    author= "김시선",
                    image = R.drawable.img_potcast_exp,
                    length = 200,
                    islike = false
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
                    islike = false
                )
            } ,
            contentClick = {}
        )
    }

}

//@RequiresApi(Build.VERSION_CODES.P)
//@Composable
//fun bottomNavigationNav(type:String, viewModel: SongViewModel, navController: NavController){
//    when(type){
//        "home" -> homeBottomNavigation(viewModel = viewModel, navController =navController )
//    }
//}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun homeBottomNavigation(viewModel: SongViewModel, navController: NavController){
    val context= LocalContext.current
    Column (modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ){
        MainMiniplayer(
            viewModel = viewModel,
            toSongActivity = { content ->
                val intent = Intent(context, SongActivity::class.java)
                context.startActivity(intent)
            } )
        BottomNavigationBar(
            navController ,
            onClick = {destination -> navController.navigate(destination.route)},
        )
    }


}

@Composable
@RequiresApi(Build.VERSION_CODES.P)
fun MainMiniplayer(viewModel : SongViewModel, toSongActivity : (Content) -> Unit){
    MiniPlayer(
        viewModel = viewModel,
        beforeSongPlayButtonClick = { /*TODO*/ },
        playSongButtonClick = { /*TODO*/ },
        nextSongPlayButtonClick = { /*TODO*/ },
        toSongActivity = toSongActivity,
        musicQueueClick = { /*TODO*/ }
    )
}


