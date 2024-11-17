package umc.study.umc_7th.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import umc.study.umc_7th.databinding.ActivityLoginBinding
import umc.study.umc_7th.ui.theme.Umc_7thTheme

class LoginActivity: ComponentActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeViewLogin.setContent {
            Umc_7thTheme {
                Scaffold { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Screen()
                    }
                }
            }
        }
    }

    @Composable
    private fun Screen() {
        LoginScreen()
    }
}

@Composable
fun LoginScreen(

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
                id = "",
                domain = "",
                password = "",
                onIdChanged = { /* TODO */ },
                onDomainChanged = { /* TODO */ },
                onPasswordChanged = { /* TODO */ },
                onLoginClicked = { /* TODO */ },
                onSignUpClicked = { /* TODO */ },
                onForgetIdClicked = { /* TODO */ },
                onForgotPasswordClicked = { /* TODO */ },
            )
            OtherLoginOptionViewer(
                onOAuthLoginClicked = { /* TODO */ },
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
                LoginScreen()
            }
        }
    }
}