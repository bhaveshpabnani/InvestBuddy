package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.myapplication.ui.components.isValidPassword

@Composable
fun CreateNewPasswordScreen(
    onBackClick: () -> Unit,
    onResetPasswordClick: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var attemptedSubmit by remember { mutableStateOf(false) }

    val passwordError = (attemptedSubmit || password.isNotBlank()) && !isValidPassword(password)
    val confirmPasswordError = (attemptedSubmit || confirmPassword.isNotBlank()) && confirmPassword != password
    val canResetPassword = isValidPassword(password) && confirmPassword == password

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackButtonTopBar(onBackClick = onBackClick)
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Text(
            text = "Create New Password",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Please enter and confirm your new password.\nYou will need to login after you reset.",
            fontSize = 15.sp,
            color = TextLight,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        CustomTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "Enter your password",
            isPassword = true,
            isError = passwordError,
            supportingText = "Use at least 8 characters with letters and numbers",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm Password",
            placeholder = "Confirm your password",
            isPassword = true,
            isError = confirmPasswordError,
            supportingText = if (confirmPasswordError) "Passwords do not match" else null,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        PrimaryButton(
            text = "Reset Password",
            enabled = canResetPassword,
            onClick = {
                attemptedSubmit = true
                if (canResetPassword) {
                    onResetPasswordClick()
                }
            }
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}
