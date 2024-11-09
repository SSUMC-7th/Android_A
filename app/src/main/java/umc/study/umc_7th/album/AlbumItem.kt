package umc.study.umc_7th.album

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import umc.study.umc_7th.R
import umc.study.umc_7th.content.albumData
import umc.study.umc_7th.main.home.BottomNavigationBar
import umc.study.umc_7th.main.home.MainActivity
import umc.study.umc_7th.ui.theme.Umc_7thTheme


class AlbumItemActiviy : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        setContent{
            Umc_7thTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.navigationBarsPadding(),
                    bottomBar = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(0.dp)
                        ) {
                            BottomNavigationBar(
                                navController,
                                onClick = { destination -> navController.navigate(destination.route) },
                            )
                        }
                    }
                        ){ innerPadding->
                        Box(modifier = Modifier.padding(innerPadding)){
                            AlbumItem()
                        }

                    }


                }
            }
        }

}
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AlbumItem(){
    val context = LocalContext.current
    Column(){
        Icon(painter = painterResource(id = R.drawable.btn_arrow_black),
            contentDescription = null,
            modifier = Modifier.clickable{
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
            })
        LazyVerticalGrid(columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 8.dp)) {
            items(albumData){ content ->
                Column(
                    modifier = Modifier.padding(vertical =0.dp)
                ){
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier= Modifier
                            .wrapContentSize()
                            .padding(vertical = 20.dp)
                    ){

                        Image(bitmap = ImageBitmap.imageResource(id = content.albumImage),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(180.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )

                        Icon(

                            painter = painterResource(id = R.drawable.btn_miniplayer_play),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.BottomEnd)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(5.dp)
                    ){
                        Text(text = content.albumTitle,
                            fontSize= 20.sp,
                            fontWeight= FontWeight.Bold,)
                        Text(text = content.author,
                        )
                    }
                }

            }
        }
    }

}


@RequiresApi(Build.VERSION_CODES.P)
@Preview(showBackground = true)
@Composable
fun PreviewAlbumItem(){
    AlbumItem()
}