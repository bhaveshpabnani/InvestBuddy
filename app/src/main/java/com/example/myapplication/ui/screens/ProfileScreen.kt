package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.*

@Composable
fun ProfileScreen(
    onDepositClick: () -> Unit = {},
    onWithdrawClick: () -> Unit = {},
    onViewHistoryClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onSaveClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
    ) {
        // ── Profile fields ────────────────────────────────────────────────────
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProfileField(
                label = "First Name",
                value = "Preet",
                modifier = Modifier.weight(1f)
            )
            ProfileField(
                label = "Last Name",
                value = "Panchal",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProfileField(
            label = "E-mail",
            value = "panchalpreet090304@gmail.com",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProfileField(
            label = "UPI ID:",
            value = "preet@ibl",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ── Deposit / Withdraw ────────────────────────────────────────────────
        DepositWithdrawRow(
            onDepositClick = onDepositClick,
            onWithdrawClick = onWithdrawClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ── PAN Details ───────────────────────────────────────────────────────
        ProfileField(
            label = "PAN Details",
            value = "GYIPP8128N",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ── History ───────────────────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "History",
                color = AppTextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .clickable { onViewHistoryClick() }
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "View Orders and Transactions",
                    color = Color(0xFF2F80ED),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textDecoration = TextDecoration.Underline
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // ── Edit / Save buttons ───────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Edit (dark)
            Button(
                onClick = onEditClick,
                modifier = Modifier.weight(1f).height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ChipBg),
                shape = RoundedCornerShape(27.dp),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text("Edit", color = AppTextWhite,
                    fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
            // Save (white)
            Button(
                onClick = onSaveClick,
                modifier = Modifier.weight(1f).height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(27.dp),
                elevation = ButtonDefaults.buttonElevation(0.dp)
            ) {
                Text("Save", color = Color.Black,
                    fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ── Reusable labelled field ───────────────────────────────────────────────────
@Composable
private fun ProfileField(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            color = AppTextWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = value,
                color = Color(0xFF1E232C),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

// ── "Edit not active" placeholder ────────────────────────────────────────────
@Composable
fun EditNotActiveScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Edit feature is not active as the\nproduct is in prototype stage",
            color = AppTextWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            lineHeight = 30.sp
        )
    }
}