package umc.study.umc_7th.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.VerticalDivider
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import umc.study.umc_7th.R

@Composable
fun LoginForm(
    id: String,
    domain: String,
    password: String,
    onIdChanged: (String) -> Unit,
    onDomainChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    onForgetIdClicked: () -> Unit,
    onForgotPasswordClicked: () -> Unit,
) {
    var showPassword by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(0.8f),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = id,
                    onValueChange = onIdChanged,
                    placeholder = {
                        Text(
                            text = "아이디(이메일)",
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    )
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
                    placeholder = {
                        Text(
                            text = "직접입력",
                            color = Color.Gray,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
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
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                    )
                )
            }

            TextField(
                value = password.let { if (showPassword) it else "*".repeat(it.length) },
                onValueChange = onPasswordChanged,
                placeholder = {
                    Text(
                        text = "비밀번호",
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
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
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                )
            )

            Column {
                Button(
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Blue,
                        contentColor = Color.White
                    ),
                    onClick = onLoginClicked,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "로그인",
                        style = TextStyle(
                            fontSize = 16.sp,
                        )
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.height(IntrinsicSize.Min),
                    ) {
                        TextButton(
                            onClick = onForgetIdClicked,
                            contentPadding = PaddingValues(4.dp)
                        ) {
                            Text(
                                text = "아이디 찾기",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                ),
                            )
                        }
                        VerticalDivider(modifier = Modifier.fillMaxHeight(0.5f))
                        TextButton(
                            onClick = onForgotPasswordClicked,
                            contentPadding = PaddingValues(4.dp)
                        ) {
                            Text(
                                text = "비밀번호 찾기",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    color = Color.Gray,
                                ),
                            )
                        }
                    }

                    TextButton(
                        onClick = onSignUpClicked,
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        Text(
                            text = "회원가입",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginForm() {
    LoginForm(
        id = "test",
        domain = "test.com",
        password = "12345",
        onIdChanged = {},
        onDomainChanged = {},
        onPasswordChanged = {},
        onLoginClicked = {},
        onSignUpClicked = {},
        onForgetIdClicked = {},
        onForgotPasswordClicked = {},
    )
}