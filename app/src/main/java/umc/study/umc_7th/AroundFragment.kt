package umc.study.umc_7th

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import android.widget.Space
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun aroundFragment(navController: NavController){
    Scaffold {
        Text(text = "around")
    }
}

@Composable
fun aroundTabRow(){
    val tabs = listOf("차트", "영상", "장르","상황","분위기","오디오")
    var selectedTabIndex by remember { mutableStateOf(0) }
    Column {
        ScrollableTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            indicator = { },
            divider = { },
            modifier = Modifier.align(Alignment.Start)
        ){
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    modifier= Modifier.padding(4.dp)
                ){
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (selectedTabIndex == index) Color.Blue else Color.LightGray,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(vertical = 8.dp, horizontal = 16.dp),
                    ){
                        Text(text = tab,
                            color = if(selectedTabIndex==index) Color.White else Color.Black,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center)

                    }

                }
        }
    }
}
}
@Composable
fun aroundScreen(){
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text("둘러 보기", fontSize = 28.sp, fontWeight = FontWeight.Bold,)
        Spacer(modifier = Modifier.height(16.dp))
        aroundTabRow()
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text("차트", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Icon(painter = painterResource(id = R.drawable.btn_main_arrow_more),
                contentDescription = null,
                modifier = Modifier.size(24.dp))
        }

    }
}

@Composable
@Preview(showBackground = true)
fun previewAroundTab(){
    aroundScreen()

}