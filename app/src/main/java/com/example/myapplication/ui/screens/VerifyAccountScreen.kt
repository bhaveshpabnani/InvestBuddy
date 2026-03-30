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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.BackButtonTopBar
import com.example.myapplication.ui.components.CustomTextField
import com.example.myapplication.ui.components.PrimaryButton
import com.example.myapplication.ui.components.TextDark
import com.example.myapplication.ui.components.TextLight
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun VerifyAccountScreen(
    email: String = "johndoe@gmail.com",
    onBackClick: () -> Unit,
    onVerifyClick: () -> Unit,
    onResendClick: () -> Unit
) {
    var code by remember { mutableStateOf("") }
    var attemptedSubmit by remember { mutableStateOf(false) }
    val codeError = attemptedSubmit && code.length != 4
    val canVerify = code.length == 4

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackButtonTopBar(onBackClick = onBackClick)
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Text(
            text = "Verify Account",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        val infoText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = TextLight, fontSize = 15.sp)) {
                append("Code has been send to ")
            }
            withStyle(style = SpanStyle(color = TextDark, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)) {
                append(email)
            }
            withStyle(style = SpanStyle(color = TextLight, fontSize = 15.sp)) {
                append(".\nEnter the code to verify your account.")
            }
        }

        Text(
            text = infoText,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        CustomTextField(
            value = code,
            onValueChange = { input ->
                code = input.filter { it.isDigit() }.take(4)
            },
            label = "Enter Code",
            placeholder = "4 Digit Code",
            isError = codeError,
            supportingText = if (codeError) "Code must contain exactly 4 digits" else "Enter the 4-digit code sent to your email",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Didn't Receive Code? ",
                color = TextLight,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Resend Code",
                color = androidx.compose.ui.graphics.Color(0xFF8391A1), // Looks similar to the screenshot, maybe underlined
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline,
                modifier = Modifier.clickable { onResendClick() }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Resend code in 00:59",
            color = TextLight,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
        
        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            text = "Verify Account",
            enabled = canVerify,
            onClick = {
                attemptedSubmit = true
                if (canVerify) {
                    onVerifyClick()
                }
            }
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun VerifyAccountScreenPreview() {
    MyApplicationTheme {
        Surface {
            VerifyAccountScreen(
                email = "johndoe@gmail.com",
                onBackClick = {},
                onVerifyClick = {},
                onResendClick = {}
            )
        }
    }
}
