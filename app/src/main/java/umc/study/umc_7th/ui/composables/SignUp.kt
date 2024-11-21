package umc.study.umc_7th.ui.composables

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import umc.study.umc_7th.LoginActivity
import umc.study.umc_7th.R
import umc.study.umc_7th.ui.viewmodel.SignUpViewModel

@Composable
fun SignUp(viewModel: SignUpViewModel) {

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordCheck by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isPasswordCheckVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "회원가입",
            modifier = Modifier.align(Alignment.TopCenter),
            style = MaterialTheme.typography.titleLarge
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp)
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
                Icon(painter = painterResource(id = if (isPasswordVisible) R.drawable.btn_input_password_off else R.drawable.btn_input_password),
                    contentDescription = "Toggle password visibility",
                    tint = Color.Unspecified
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = passwordCheck,
                onValueChange = { passwordCheck = it },
                label = { Text("비밀번호 확인") },
                visualTransformation = if (isPasswordCheckVisible) VisualTransformation.None else PasswordVisualTransformation()
            )
            IconButton(
                onClick = { isPasswordCheckVisible = !isPasswordCheckVisible },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
            ) {
                Icon(painter = painterResource(id = if (isPasswordCheckVisible) R.drawable.btn_input_password_off else R.drawable.btn_input_password),
                    contentDescription = "Toggle password visibility",
                    tint = Color.Unspecified
                )
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Button(
            onClick = {
                if (password == passwordCheck) {
                    viewModel.registerUser(email, password, object : NetworkViewInterface {
                        override fun onLoading() {
                            // 로딩 중 처리
                        }

                        override fun onSuccess(result: Any) {
                            Toast.makeText(context, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                        }

                        override fun onError(errorMessage: String) {
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    },
                        context
                    )
                } else {
                    Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text("가입 완료")
        }
    }
}

@Preview
@Composable
fun PreviewSignUp() {
    SignUp(SignUpViewModel())
}