package com.example.myapplication.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.BackButtonTopBar
import com.example.myapplication.ui.components.CustomTextField
import com.example.myapplication.ui.components.PrimaryButton
import com.example.myapplication.ui.components.SocialLoginButton
import com.example.myapplication.ui.components.TextDark
import com.example.myapplication.ui.components.TextLight
import com.example.myapplication.ui.components.isValidEmail
import com.example.myapplication.ui.components.isValidPassword

@Composable
fun LoginScreen(
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onGoogleLoginClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var attemptedSubmit by remember { mutableStateOf(false) }

    val normalizedEmail = email.trim()
    val emailError = (attemptedSubmit || email.isNotBlank()) && !isValidEmail(normalizedEmail)
    val passwordError = (attemptedSubmit || password.isNotBlank()) && !isValidPassword(password)
    val canLogin = isValidEmail(normalizedEmail) && isValidPassword(password)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackButtonTopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Login",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Sign in to track your portfolio and strategy performance.",
            color = TextLight,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "E-mail",
            placeholder = "Enter your email",
            isError = emailError,
            supportingText = if (emailError) "Enter a valid email address" else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "Enter your password",
            isPassword = true,
            isError = passwordError,
            supportingText = if (passwordError) "Use at least 8 characters with letters and numbers" else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Forgot Password?",
                color = androidx.compose.ui.graphics.Color(0xFF2F80ED),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { onForgotPasswordClick() }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = "Login",
            enabled = canLogin,
            onClick = {
                attemptedSubmit = true
                if (canLogin) {
                    onLoginClick()
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "or login with",
            color = TextLight,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(24.dp))

        SocialLoginButton(
            text = "Login with Google",
            onClick = onGoogleLoginClick
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}
