package com.example.myapplication.ui.components

import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val PrimaryGreen = Color(0xFF349864)
val TextDark = Color(0xFF1E232C)
val TextLight = Color(0xFF8391A1)
val BorderColor = Color(0xFFE8ECF4)
private val FieldContainer = Color(0xFFF7F9FC)
private val DisabledGreen = Color(0xFF87B79F)
private val ErrorRed = Color(0xFFD93025)

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryGreen,
            disabledContainerColor = DisabledGreen,
            contentColor = Color.White,
            disabledContentColor = Color.White.copy(alpha = 0.75f)
        ),
        shape = RoundedCornerShape(28.dp)
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        border = BorderStroke(1.dp, BorderColor),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextDark)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // Google icon usually comes from res, but we can use a Box placeholder or Material Icon if we don't have it.
            // Assuming we don't have R.drawable.ic_google, we'll draw a placeholder or just rely on a default icon.
            Text("G", fontWeight = FontWeight.Bold, color = Color.Red, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isPassword: Boolean = false,
    isError: Boolean = false,
    supportingText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = 1,
    modifier: Modifier = Modifier
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = label, color = TextDark, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder, color = TextLight) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryGreen,
                unfocusedBorderColor = BorderColor,
                focusedContainerColor = FieldContainer,
                unfocusedContainerColor = FieldContainer,
                errorBorderColor = ErrorRed,
                errorContainerColor = FieldContainer,
                errorCursorColor = ErrorRed,
                cursorColor = PrimaryGreen,
            ),
            singleLine = true,
            maxLines = maxLines,
            keyboardOptions = keyboardOptions,
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = TextLight
                        )
                    }
                }
            } else null
        )

        if (supportingText != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = supportingText,
                color = if (isError) ErrorRed else TextLight,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun BackButtonTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = FieldContainer,
            border = BorderStroke(1.dp, BorderColor)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextDark
                )
            }
        }
    }
}

fun isValidEmail(value: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(value.trim()).matches()
}

fun isValidPassword(value: String): Boolean {
    if (value.length < 8) return false
    val hasLetter = value.any { it.isLetter() }
    val hasDigit = value.any { it.isDigit() }
    return hasLetter && hasDigit
}
