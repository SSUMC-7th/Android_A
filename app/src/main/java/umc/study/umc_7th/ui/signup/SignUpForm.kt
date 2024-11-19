package umc.study.umc_7th.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.R

@Composable
fun SignUpForm(
    id: String,
    domain: String,
    password: String,
    confirmPassword: String,
    onIdChanged: (String) -> Unit,
    onDomainChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
) {
    val colors = TextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
    )

    val placeholder: @Composable ((String) -> Unit) = {
        Text(
            text = it,
            color = Color.Gray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "회원가입",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
            )
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = id,
                    onValueChange = onIdChanged,
                    placeholder = { placeholder("아이디(이메일)") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = colors,
                )
                Text(
                    text = "@",
                    style = TextStyle(
                        fontSize = 16.sp,
                    )
                )
                TextField(
                    value = domain,
                    onValueChange = onDomainChanged,
                    placeholder = { placeholder("직접입력") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    trailingIcon = {
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.nugu_btn_down),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color.Gray
                            )
                        }
                    },
                    colors = colors,
                )
            }

            TextField(
                value = password,
                onValueChange = onPasswordChanged,
                visualTransformation = if (showPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                placeholder = { placeholder("비밀번호") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(
                        onClick = { showPassword = !showPassword },
                    ) {
                        Image(
                            painter = painterResource(
                                id = if (showPassword)
                                    R.drawable.btn_input_password_off
                                else
                                    R.drawable.btn_input_password
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = colors,
            )

            TextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChanged,
                visualTransformation = if (showConfirmPassword) VisualTransformation.None
                else PasswordVisualTransformation(),
                placeholder = { placeholder("비밀번호 확인") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(
                        onClick = { showConfirmPassword = !showConfirmPassword },
                    ) {
                        Image(
                            painter = painterResource(
                                id = if (showConfirmPassword)
                                    R.drawable.btn_input_password_off
                                else
                                    R.drawable.btn_input_password
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = colors,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUpForm() {
    SignUpForm(
        id = "test",
        domain = "test.com",
        password = "12345",
        confirmPassword = "12345",
        onIdChanged = {},
        onDomainChanged = {},
        onPasswordChanged = {},
        onConfirmPasswordChanged = {},
    )
}