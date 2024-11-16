package umc.study.umc_7th

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun signup(onSignUpClick :(String, String,String) -> Unit){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var namePart by remember { mutableStateOf("") }
    var domainPart by remember { mutableStateOf("gmail.com") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            ,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        Text(text = "회원 가입", fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(32.dp))
        Box(modifier = Modifier.fillMaxWidth(),
        )
        {
            TextField(value = namePart,
                onValueChange ={namePart = it},
                label = { Text("아이디(이메일)") },
                modifier = Modifier
                    .width(140.dp)
                    .height(32.dp)
                    .align(Alignment.CenterStart),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)

            )
            Text("@", fontSize = 16.sp, modifier = Modifier.align(Alignment.Center));
            TextField(value = namePart,
                onValueChange ={namePart = it},
                label = { Text("직접 입력") },
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
        Box(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("비밀번호 확인") },
                modifier = Modifier
                    .fillMaxWidth()
                    .width(50.dp)
                    .height(32.dp)
                    .align(Alignment.CenterStart),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)

            )
        }
        Spacer(modifier = Modifier.height(400.dp))
        TextButton(onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue),
            ) {
            Text("가입 완료", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewSignup(){
    signup(onSignUpClick = {email, password, name -> println("Email: $email, Password: $password, Name: $name")})
}