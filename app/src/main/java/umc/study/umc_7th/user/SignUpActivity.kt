package umc.study.umc_7th.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.main.home.MainActivity

class SignUpActivity : ComponentActivity(){
    private val userViewModel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            SignUpActivity(onSignUpClick = {email, password, repassword ->
                if(password != repassword){
                    val name = "ean"
                    userViewModel.signUp(name, email, password,
                        onSuccess={
                            Toast.makeText(this, "성공", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        },
                        onError = {error ->
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        })
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, "다시 시도", Toast.LENGTH_SHORT).show()
                }
        })
    }
}

@SuppressLint("NotConstructor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpActivity(onSignUpClick :(String, String,String) -> Unit){
    var password by remember { mutableStateOf("") }
    var namePart by remember { mutableStateOf("") }
    var domainPart by remember { mutableStateOf("") }
    var repassword by remember { mutableStateOf("")}
    Column(
        modifier = Modifier
            .fillMaxSize().padding(8.dp)
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
                placeholder = { Text("아이디(이메일)") },
                modifier = Modifier
                    .width(140.dp)
                    .height(56.dp)
                    .align(Alignment.CenterStart),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)

            )
            Text("@", fontSize = 16.sp, modifier = Modifier.align(Alignment.Center));
            TextField(value = domainPart,
                onValueChange ={domainPart = it},
                placeholder = { Text("직접 입력") },
                modifier = Modifier
                    .width(140.dp)
                    .height(56.dp)
                    .align(Alignment.CenterEnd),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("비밀번호") },
                modifier = Modifier
                    .fillMaxWidth()
                    .width(50.dp)
                    .height(56.dp)
                    .align(Alignment.CenterStart),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)

            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = repassword,
                onValueChange = { repassword = it },
                placeholder = { Text("비밀번호 확인") },
                modifier = Modifier
                    .fillMaxWidth()
                    .width(50.dp)
                    .height(56.dp)
                    .align(Alignment.CenterStart),
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)

            )
        }
        Spacer(modifier = Modifier.height(300.dp))
        TextButton(onClick = {onSignUpClick(namePart, password, repassword)},
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                ,
            ) {
            Text("가입 완료", color = Color.White)
        }
    }
}}

//@Preview(showBackground = true)
//@Composable
//fun previewSignup(){
//    SignUpActivity(onSignUpClick = {email, password, name -> println("Email: $email, Password: $password, Name: $name")})
//}