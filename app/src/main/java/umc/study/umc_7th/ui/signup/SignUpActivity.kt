package umc.study.umc_7th.ui.signup

import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.databinding.ActivitySignupBinding
import umc.study.umc_7th.ui.theme.Umc_7thTheme

class SignUpActivity: ComponentActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.composeViewSignUp.setContent {
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

    }
}

@Composable
fun SignUpScreen(

) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize(),
    ) {
        SignUpForm(
            id = "",
            domain = "",
            password = "",
            confirmPassword = "",
            onIdChanged = { /* TODO */ },
            onDomainChanged = { /* TODO */ },
            onPasswordChanged = { /* TODO */ },
            onConfirmPasswordChanged = { /* TODO */ },
        )

        Button(
            onClick = { /* TODO */ },
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White,
            ),
            contentPadding = PaddingValues(12.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "가입완료",
                style = TextStyle(
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
                SignUpScreen()
            }
        }
    }
}