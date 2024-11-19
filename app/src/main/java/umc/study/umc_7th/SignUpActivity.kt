package umc.study.umc_7th

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import umc.study.umc_7th.ui.composables.SignUp
import umc.study.umc_7th.ui.viewmodel.SignUpViewModel

class SignUpActivity : ComponentActivity() {

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignUpScreen(
                viewModel,
                onNavigateToLogin = {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onNavigateToLogin: () -> Unit
) {
    val context = LocalContext.current

    SignUp(
        onSignUpClicked = { email, password, passwordCheck ->
            viewModel.signUpWithEmail(email, password, passwordCheck) { success, result ->
                if (success) {
                    // Handle success (e.g., navigate to another screen)
                    Log.d("SignUp", "JWT Token: $result")
                    Toast.makeText(context, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                    onNavigateToLogin()
                } else {
                    // Handle error (e.g., show a Toast)
                    Log.d("SignUp", "Error: $result")
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewSignUpScreen() {
    val viewModel = SignUpViewModel()
    SignUpScreen(viewModel, onNavigateToLogin = {})
}