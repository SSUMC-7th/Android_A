package umc.study.umc_7th

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import umc.study.umc_7th.ui.composables.Login
import umc.study.umc_7th.ui.viewmodel.LoginViewModel

class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(
                viewModel = viewModel,
                onNavigateToSignUp = {
                    val intent = Intent(this, SignUpActivity::class.java)
                    startActivity(intent)
                    finish()
                },
                onLoginSuccess = {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onNavigateToSignUp: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    Login(
        viewModel = viewModel,
        onSignUpClicked = {
            onNavigateToSignUp()
        },
        onLoginSuccess = {
            onLoginSuccess()
        }
    )
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        viewModel = LoginViewModel(),
        onNavigateToSignUp = {},
        onLoginSuccess = {}
    )
}