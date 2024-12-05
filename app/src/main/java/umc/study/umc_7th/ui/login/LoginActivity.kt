package umc.study.umc_7th.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import umc.study.umc_7th.databinding.ActivityLoginBinding
import umc.study.umc_7th.ui.main.MainActivity
import umc.study.umc_7th.ui.signup.SignUpActivity
import umc.study.umc_7th.ui.theme.Umc_7thTheme

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private val snackBarHost = SnackbarHostState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeViewLogin.setContent {
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
        var showScreen by remember { mutableStateOf(false) }

        var id by remember { mutableStateOf("") }
        var domain by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        LaunchedEffect(key1 = true) {
            viewModel.testLogin(onSuccess = {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, onFailed = {
                showScreen = true
            })
        }

        if (showScreen) LoginScreen(
            id = id,
            domain = domain,
            password = password,
            onIdChanged = { id = it },
            onDomainChanged = { domain = it },
            onPasswordChanged = { password = it },
            onLoginClicked = {
                viewModel.login(
                    email = "$id@$domain",
                    password = password,
                    onSuccess = { onLoginSuccess() },
                    onRejected = {
                        snackBarHost.currentSnackbarData?.dismiss()
                        snackBarHost.showSnackbar(
                            message = "이메일 또는 비밀번호가 틀렸습니다",
                            withDismissAction = true,
                        )
                    },
                    onFailed = { /* TODO */ },
                )
            },
            onSignUpClicked = {
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            },
            onOAuthLoginClicked = {
                if (it == OAuthProvider.KAKAO) viewModel.loginWithKakao(
                    onSuccess = { onLoginSuccess() },
                    onFailed = { /* TODO */ },
                )
            }
        )
    }

    private fun onLoginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun LoginScreen(
    id: String,
    domain: String,
    password: String,
    onIdChanged: (String) -> Unit,
    onDomainChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    onOAuthLoginClicked: (OAuthProvider) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.fillMaxSize(),
    ) {
        TitleIcon()
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
        ) {
            LoginForm(
                id = id,
                domain = domain,
                password = password,
                onIdChanged = onIdChanged,
                onDomainChanged = onDomainChanged,
                onPasswordChanged = onPasswordChanged,
                onLoginClicked = onLoginClicked,
                onSignUpClicked = onSignUpClicked,
                onForgotIdClicked = { /* TODO */ },
                onForgotPasswordClicked = { /* TODO */ },
            )
            OtherLoginOptionViewer(
                onOAuthLoginClicked = onOAuthLoginClicked,
                onSktIdLoginClicked = { /* TODO */ },
                onPhoneNumberLoginClicked = { /* TODO */ },
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    Umc_7thTheme {
        Scaffold { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                LoginScreen(
                    id = "",
                    domain = "",
                    password = "",
                    onIdChanged = {},
                    onDomainChanged = {},
                    onPasswordChanged = {},
                    onLoginClicked = {},
                    onSignUpClicked = {},
                    onOAuthLoginClicked = {},
                )
            }
        }
    }
}