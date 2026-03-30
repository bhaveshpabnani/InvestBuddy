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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.BackButtonTopBar
import com.example.myapplication.ui.components.CustomTextField
import com.example.myapplication.ui.components.PrimaryButton
import com.example.myapplication.ui.components.TextDark
import com.example.myapplication.ui.components.TextLight
import com.example.myapplication.ui.components.isValidEmail
import com.example.myapplication.ui.components.isValidPassword

@Composable
fun RegisterScreen(
    onBackClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var attemptedSubmit by remember { mutableStateOf(false) }

    val compactLayout = LocalConfiguration.current.screenWidthDp < 390
    val normalizedEmail = email.trim()
    val firstNameError = (attemptedSubmit || firstName.isNotBlank()) && firstName.trim().length < 2
    val lastNameError = (attemptedSubmit || lastName.isNotBlank()) && lastName.trim().length < 2
    val emailError = (attemptedSubmit || email.isNotBlank()) && !isValidEmail(normalizedEmail)
    val passwordError = (attemptedSubmit || password.isNotBlank()) && !isValidPassword(password)
    val confirmPasswordError = (attemptedSubmit || confirmPassword.isNotBlank()) && confirmPassword != password

    val canCreateAccount = firstName.trim().length >= 2 &&
        lastName.trim().length >= 2 &&
        isValidEmail(normalizedEmail) &&
        isValidPassword(password) &&
        confirmPassword == password

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackButtonTopBar(onBackClick = onBackClick)
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Text(
            text = "Register",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        if (compactLayout) {
            CustomTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = "First Name",
                placeholder = "First Name",
                isError = firstNameError,
                supportingText = if (firstNameError) "Enter at least 2 characters" else null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = "Last Name",
                placeholder = "Last Name",
                isError = lastNameError,
                supportingText = if (lastNameError) "Enter at least 2 characters" else null,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        } else {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                CustomTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = "First Name",
                    placeholder = "First Name",
                    isError = firstNameError,
                    supportingText = if (firstNameError) "Enter at least 2 characters" else null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                CustomTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = "Last Name",
                    placeholder = "Last Name",
                    isError = lastNameError,
                    supportingText = if (lastNameError) "Enter at least 2 characters" else null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
            supportingText = if (passwordError) {
                "Use at least 8 characters with letters and numbers"
            } else {
                "Use at least 8 characters with letters and numbers"
            },
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

        Spacer(modifier = Modifier.height(40.dp))

        PrimaryButton(
            text = "Create Account",
            enabled = canCreateAccount,
            onClick = {
                attemptedSubmit = true
                if (canCreateAccount) {
                    onCreateAccountClick()
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        val annotatedString = buildAnnotatedString {
            withStyle(style = SpanStyle(color = TextDark)) {
                append("By continuing, you agree to our ")
            }
            pushStringAnnotation(tag = "terms", annotation = "terms")
            withStyle(style = SpanStyle(color = androidx.compose.ui.graphics.Color(0xFF2F80ED), fontWeight = FontWeight.SemiBold)) {
                append("Terms of Service")
            }
            pop()
            withStyle(style = SpanStyle(color = TextDark)) {
                append(" and\n")
            }
            pushStringAnnotation(tag = "privacy", annotation = "privacy")
            withStyle(style = SpanStyle(color = androidx.compose.ui.graphics.Color(0xFF2F80ED), fontWeight = FontWeight.SemiBold)) {
                append("Privacy Policy")
            }
            pop()
            withStyle(style = SpanStyle(color = TextDark)) {
                append(".")
            }
        }

        androidx.compose.foundation.text.ClickableText(
            text = annotatedString,
            onClick = { offset ->
                annotatedString.getStringAnnotations(tag = "terms", start = offset, end = offset)
                    .firstOrNull()?.let { onTermsClick() }
                annotatedString.getStringAnnotations(tag = "privacy", start = offset, end = offset)
                    .firstOrNull()?.let { onPrivacyClick() }
            },
            modifier = Modifier.fillMaxWidth(),
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        )
        
        Spacer(modifier = Modifier.height(40.dp))
    }
}
