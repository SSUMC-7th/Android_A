package umc.study.umc_7th.ui.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.study.umc_7th.databinding.ActivitySignupBinding
import umc.study.umc_7th.ui.theme.Umc_7thTheme

@AndroidEntryPoint
class SignUpActivity : ComponentActivity() {
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: ActivitySignupBinding
    private val snackBarHost = SnackbarHostState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeViewSignUp.setContent {
            Umc_7thTheme {
                Scaffold(snackbarHost = { SnackbarHost(snackBarHost) }) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Screen()
                    }
                }
            }
        }
    }

    @Composable
    private fun Screen() {
        var id by remember { mutableStateOf("") }
        var domain by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        val scope = rememberCoroutineScope()

        SignUpScreen(
            id = id,
            domain = domain,
            password = password,
            confirmPassword = confirmPassword,
            onIdChanged = { id = it },
            onDomainChanged = { domain = it },
            onPasswordChanged = { password = it },
            onConfirmPasswordChanged = { confirmPassword = it },
            onSignUpButtonClicked = {
                if (password != confirmPassword) {
                    scope.launch { showMessage("비밀번호가 일치하지 않습니다") }
                    return@SignUpScreen
                }

                viewModel.signUp(
                    email = "$id@$domain",
                    password = password,
                    onSuccess = {
                        setResult(RESULT_OK)
                        finish()
                    },
                    onInvalidEmail = { showMessage("이메일 형식이 올바르지 않습니다") },
                    onInvalidPassword = { showMessage("비밀번호 형식이 올바르지 않습니다") },
                    onAlreadySigned = { showMessage("이미 가입된 이메일입니다") },
                    onFailure = { /* TODO */ },
                )
            },
        )
    }

    private suspend fun showMessage(msg: String) {
        snackBarHost.currentSnackbarData?.dismiss()
        snackBarHost.showSnackbar(
            message = msg,
            withDismissAction = true,
        )
    }
}

@Composable
fun SignUpScreen(
    id: String,
    domain: String,
    password: String,
    confirmPassword: String,
    onIdChanged: (String) -> Unit,
    onDomainChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onSignUpButtonClicked: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize(),
    ) {
        SignUpForm(
            id = id,
            domain = domain,
            password = password,
            confirmPassword = confirmPassword,
            onIdChanged = onIdChanged,
            onDomainChanged = onDomainChanged,
            onPasswordChanged = onPasswordChanged,
            onConfirmPasswordChanged = onConfirmPasswordChanged,
        )

        Button(
            onClick = onSignUpButtonClicked,
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White,
            ),
            contentPadding = PaddingValues(12.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "가입완료", style = TextStyle(
                    fontSize = 16.sp,
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSignUpScreen() {
    Umc_7thTheme {
        Scaffold { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                SignUpScreen(
                    id = "",
                    domain = "",
                    password = "",
                    confirmPassword = "",
                    onIdChanged = {},
                    onDomainChanged = {},
                    onPasswordChanged = {},
                    onConfirmPasswordChanged = {},
                    onSignUpButtonClicked = {},
                )
            }
        }
    }
}