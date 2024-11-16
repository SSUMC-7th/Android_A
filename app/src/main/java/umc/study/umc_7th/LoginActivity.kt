package umc.study.umc_7th

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class LoginActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun loginActivity(onLoginClick :(String, String) -> Unit){
    var password by remember { mutableStateOf("") }
    var namePart by remember { mutableStateOf("") }
    var domainPart by remember { mutableStateOf("gmail.com") }

    val domainOption = listOf("직접 입력","gmail.com", "naver.com", "nate.com", "daum.net")

    Spacer(modifier = Modifier.height(64.dp))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)

    ){
        Image(painter= painterResource(id = R.drawable.ic_flo_logo), contentDescription =null )
        Spacer(modifier = Modifier.height(32.dp))
        Box(modifier = Modifier.fillMaxWidth(),
            )
        {
            TextField(value = namePart,
                onValueChange ={namePart = it},
                label = {Text("아이디(이메일)")},
                modifier = Modifier
                    .width(140.dp)
                    .height(32.dp)
                    .align(Alignment.CenterStart),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)

                )
            Text("@", fontSize = 16.sp, modifier = Modifier.align(Alignment.Center));
            TextField(value = namePart,
                onValueChange ={namePart = it},
                label = {Text("직접 입력")},
                modifier = Modifier
                    .width(140.dp)
                    .height(32.dp)
                    .align(Alignment.CenterEnd),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("비밀번호") },
                modifier = Modifier
                    .fillMaxWidth()
                    .width(50.dp)
                    .height(32.dp)
                    .align(Alignment.CenterStart),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)

            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column {
            TextButton(onClick = { /*TODO*/ },
                modifier = Modifier
                    .background(Color.Blue)
                    .fillMaxWidth()) {
                Text(text = "로그인", color =Color.White)
            }
            Box(
                modifier = Modifier.fillMaxWidth(),

            ){
                Row(
                    horizontalArrangement = Arrangement.Start
                ){
                    Text(text = "아이디 찾기 | ", fontSize = 10.sp,
                        color = Color.Black.copy(alpha = 0.7f))
                    Text(text = "비밀번호 찾기", fontSize = 10.sp,
                        color= Color.Black.copy(alpha = 0.7f))

                }
                Text(text = "회원가입", fontSize = 10.sp,
                    color= Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )

            }
        }
        OutlinedButton(onClick = { /*TODO*/ },
            shape= RectangleShape,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)) {
            Icon(painter = painterResource(id = R.drawable.ico_20_logo_tid_white),
                contentDescription =null,
                tint = Color.Unspecified)
            Text("아이디로 로그인", color = Color.White)
        }
        OutlinedButton(onClick = { /*TODO*/ },
            shape= RectangleShape,
            modifier = Modifier.fillMaxWidth()) {
            Icon(painter = painterResource(id = R.drawable.btn_setting_phone),
                contentDescription =null,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp))
            Text("휴대폰 번호 로그인", color = Color.Black)
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 74.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Image(painter = painterResource(id = R.drawable.naver_44), contentDescription =null,
                modifier = Modifier.size(44.dp))
            Image(painter = painterResource(id = R.drawable.apple_44), contentDescription =null ,
                modifier = Modifier.size(44.dp))
            Image(painter = painterResource(id = R.drawable.kakako_44), contentDescription =null ,
                modifier = Modifier.size(44.dp))

        }


    }
}


@Composable
@Preview(showBackground = true)
fun previewLoginActivity(){
    loginActivity(onLoginClick = {email, password -> println("Email: $email, Password: $password")})
}