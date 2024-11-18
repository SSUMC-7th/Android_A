package umc.study.umc_7th

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
                onLoginResult = { success, message ->
                    if (success) {
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, message ?: "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                },
                onNavigateToSignUp = {
                    val intent = Intent(this, SignUpActivity::class.java)
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
    onLoginResult: (Boolean, String?) -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    Login(
        viewModel = viewModel,
        onLoginResult = { success, message ->
            onLoginResult(success, message)
        },
        onSignUpClicked = {
            onNavigateToSignUp()
        }
    )
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(
        viewModel = LoginViewModel(),
        onLoginResult = { success, message ->
        },
        onNavigateToSignUp = {}
    )
}