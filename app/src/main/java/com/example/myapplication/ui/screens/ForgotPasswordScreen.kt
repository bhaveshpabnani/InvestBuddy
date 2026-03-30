package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.BackButtonTopBar
import com.example.myapplication.ui.components.CustomTextField
import com.example.myapplication.ui.components.PrimaryButton
import com.example.myapplication.ui.components.TextDark
import com.example.myapplication.ui.components.TextLight
import com.example.myapplication.ui.components.isValidEmail

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit,
    onSendInstructionClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var attemptedSubmit by remember { mutableStateOf(false) }

    val normalizedEmail = email.trim()
    val emailError = (attemptedSubmit || email.isNotBlank()) && !isValidEmail(normalizedEmail)
    val canContinue = isValidEmail(normalizedEmail)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackButtonTopBar(onBackClick = onBackClick)
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Text(
            text = "Forgot Password",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No worries! Enter your email address below and we will send you a code to reset password.",
            fontSize = 15.sp,
            color = TextLight,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
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
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = "Send Reset Instruction",
            enabled = canContinue,
            onClick = {
                attemptedSubmit = true
                if (canContinue) {
                    onSendInstructionClick()
                }
            }
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}
