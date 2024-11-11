package umc.study.umc_7th.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import umc.study.umc_7th.R
import umc.study.umc_7th.SongViewModel

enum class DestinationClass(
    val route : String,
    val mean : String,
    val currentDestinationIcon : Int,
    val notCurrentDestinationIcon : Int,
){
    Home(
        "homeFragment","홈", R.drawable.ic_bottom_home_select, R.drawable.ic_bottom_home_no_select
    ),
    Around(
        "aroundFragment","둘러보기",
        R.drawable.ic_bottom_look_select,
        R.drawable.ic_bottom_look_no_select
    ),
    Search (
        "searchFragment","검색",
        R.drawable.ic_bottom_search_select,
        R.drawable.ic_bottom_search_no_select
    ),
    Storage(
        "lockerFragment","보관함", R.drawable.ic_bottom_my_select, R.drawable.ic_bottom_my_no_select
    );
    companion object{
        val entries = values().toList()
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    onClick : (DestinationClass) -> Unit,
){
    var currentDestination by remember {
        mutableStateOf(DestinationClass.Home)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .background(color=Color.Unspecified)
    ){
        DestinationClass.entries.forEach { destination ->
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(bitmap= if (destination == currentDestination)
                    ImageBitmap.imageResource(id = destination.currentDestinationIcon)
                    else
                        ImageBitmap.imageResource(id = destination.notCurrentDestinationIcon),
                    contentDescription =null,
                    modifier = Modifier.size(24.dp).clickable {
                        currentDestination = destination
                        onClick(destination)
                        navController.navigate(destination.route)},
                    tint = Color.Unspecified)

                Text(text = destination.mean,
                    color = if(destination ==currentDestination)
                Color.Blue
                else
                Color.Black.copy(alpha= 0.5f))
            }
        }
    }
}

@Composable
fun LockerBottomBar(viewModel : SongViewModel,
                    hideBottomBar : () -> Unit){
    Row(
        modifier = Modifier.background(Color.Blue).fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,

    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(bitmap= ImageBitmap.imageResource(id =R.drawable.btn_editbar_play ),
                contentDescription =null,
                modifier = Modifier.size(28.dp),
                tint = Color.Unspecified)

            Text(text = "듣기",
                color = Color.White)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(bitmap= ImageBitmap.imageResource(id =R.drawable.btn_editbar_addplaylist ),
                contentDescription =null,
                modifier = Modifier.size(28.dp),
                tint = Color.Unspecified)

            Text(text = "재생목록",
                color = Color.White)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Icon(bitmap= ImageBitmap.imageResource(id =R.drawable.btn_editbar_mylist ),
                contentDescription =null,
                modifier = Modifier.size(28.dp),
                tint = Color.Unspecified)

            Text(text = "내 리스트",
                color = Color.White)
        }
        Column(
            modifier = Modifier.clickable { viewModel.unlikeAllSongs()
            hideBottomBar()}
        ){
            Icon(bitmap= ImageBitmap.imageResource(id =R.drawable.btn_editbar_delete ),
                contentDescription =null,
                modifier = Modifier.size(28.dp),
                tint = Color.Unspecified)

            Text(text = "삭제",
                color = Color.White)
        }

    }
}

