package umc.study.umc_7th.ui.composables

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import umc.study.umc_7th.R

@Composable
fun SignUp(onSignUpClicked: (String, String, String) -> Unit) {
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
            onClick = { onSignUpClicked(email, password, passwordCheck) },
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
    SignUp(
        onSignUpClicked = { email, password, passwordCheck ->
        // Handle sign-up logic here
    })
}