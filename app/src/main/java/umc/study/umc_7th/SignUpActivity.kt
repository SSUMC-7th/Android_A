package umc.study.umc_7th

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import umc.study.umc_7th.ui.composables.SignUp
import umc.study.umc_7th.ui.viewmodel.SignUpViewModel

class SignUpActivity : ComponentActivity() {

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SignUpScreen(viewModel)
        }
    }
}

@Composable
fun SignUpScreen(viewModel: SignUpViewModel) {

    SignUp(viewModel = viewModel)
}

@Preview
@Composable
fun PreviewSignUpScreen() {
    val viewModel = SignUpViewModel()
    SignUpScreen(viewModel)
}