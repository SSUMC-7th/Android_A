package umc.study.umc_7th.ui.composables

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import umc.study.umc_7th.R
import umc.study.umc_7th.ui.viewmodel.LoginViewModel

@Composable
fun Login(
    viewModel: LoginViewModel,
    onSignUpClicked: () -> Unit,
    onLoginSuccess: () -> Unit
) {

    val context = LocalContext.current
  
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_flo_logo),
            contentDescription = "Logo",
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
            .padding(horizontal = 16.dp),
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("아이디(이메일)") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("비밀번호") },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )
            IconButton(
                onClick = { isPasswordVisible = !isPasswordVisible },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    painter = painterResource(id = if (isPasswordVisible) R.drawable.btn_input_password_off else R.drawable.btn_input_password),
                    contentDescription = "Toggle password visibility",
                    tint = Color.Unspecified
                )
            }
        }
        Button(
            onClick = {
                viewModel.loginUser(email, password, object : NetworkViewInterface {
                    override fun onLoading() {
                        // 로딩 중 처리
                    }

                    override fun onSuccess(result: Any) {
                        Toast.makeText(context, "로그인 성공!", Toast.LENGTH_SHORT).show()
                        onLoginSuccess()
                    }

                    override fun onError(errorMessage: String) {
                        Toast.makeText(context, "로그인 실패: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                },
                    context
                ) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
            ) {
            Text(text = "로그인")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "아이디 찾기 | 비밀번호 찾기")
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { onSignUpClicked() }) {
                Text(text = "회원가입")
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        TextButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(painter = painterResource(id = R.drawable.ico_20_logo_tid_white),
                contentDescription = "TLogin")
            Text(text = "아이디로 로그인")
        }
        TextButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.textButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(painter = painterResource(id = R.drawable.btn_setting_phone),
                contentDescription = "PhoneLogin",
                modifier = Modifier.size(40.dp)
            )
            Text(text = "휴대폰 번호 로그인")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.naver_44),
                    contentDescription = "naver",
                    tint = Color.Unspecified
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.kakako_44),
                    contentDescription = "kakao",
                    tint = Color.Unspecified
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.apple_44),
                    contentDescription = "apple",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginPreview() {
    Login(
        viewModel = LoginViewModel(),
        onSignUpClicked = {},
        onLoginSuccess = {}
    )
}
