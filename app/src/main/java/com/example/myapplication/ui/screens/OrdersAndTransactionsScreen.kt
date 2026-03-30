package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.*
import com.example.myapplication.ui.model.SampleData
import com.example.myapplication.ui.model.Transaction
import com.example.myapplication.ui.model.TransactionType

private val FILTER_TABS = listOf("Stocks", "MFs", "Bonds", "Strategies", "Transfers", "All")

@Composable
fun OrdersAndTransactionsScreen(
    onBackClick: () -> Unit = {}
) {
    var selectedFilter by remember { mutableStateOf("All") }
    val transactions = remember(selectedFilter) { SampleData.transactionsFor(selectedFilter) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        // ── Title ────────────────────────────────────────────────────────────
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Orders and Transactions",
            color = AppTextWhite,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // ── Filter chips (2 rows × 3) ────────────────────────────────────────
        val rows = FILTER_TABS.chunked(3)
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowItems.forEach { tab ->
                    FilterChip(
                        label = tab,
                        selected = selectedFilter == tab,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedFilter = tab }
                    )
                }
                // Fill remaining slots if row has < 3 items
                repeat(3 - rowItems.size) { Spacer(modifier = Modifier.weight(1f)) }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(color = DividerColor, thickness = 0.5.dp)

        // ── Transaction list ─────────────────────────────────────────────────
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Group by date
            val grouped = transactions.groupBy { it.date }
            grouped.forEach { (date, txns) ->
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = date,
                        color = AppTextWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
                items(txns) { tx ->
                    TransactionRow(tx = tx)
                }
            }
        }
    }
}

// ── Filter chip ───────────────────────────────────────────────────────────────
@Composable
private fun FilterChip(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) Color.Transparent else ChipBg)
            .then(
                if (selected) Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Transparent)
                else Modifier
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // White border for selected chip
        if (selected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Transparent)
                    .then(
                        Modifier.padding(1.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(20.dp),
                    color = Color.Transparent,
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, Color.White)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            label,
                            color = AppTextWhite,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        } else {
            Text(
                label,
                color = AppTextGrey,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// ── Single transaction row ────────────────────────────────────────────────────
@Composable
private fun TransactionRow(tx: Transaction) {
    val typeLabel = when (tx.type) {
        TransactionType.BUY      -> "BUY"
        TransactionType.SELL     -> "SELL"
        TransactionType.WITHDRAW -> "Withdraw"
        TransactionType.DEPOSIT  -> "DEPOSIT"
    }
    val typeColor = when (tx.type) {
        TransactionType.BUY     -> BuyGreen
        TransactionType.SELL    -> SellRed
        TransactionType.WITHDRAW -> WithdrawColor
        TransactionType.DEPOSIT  -> DepositColor
    }
    val isTransfer = tx.deliveryType == "Transfer"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        // Left: type, name, time
        Column(modifier = Modifier.weight(1f)) {
            Text(typeLabel, color = typeColor,
                fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(tx.name, color = AppTextWhite,
                fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Text(tx.time, color = AppTextGrey, fontSize = 12.sp)
        }

        // Right: delivery type + quantity / amount
        Column(horizontalAlignment = Alignment.End) {
            Text(tx.deliveryType, color = AppTextGrey,
                fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Text(tx.quantityOrAmount, color = AppTextWhite,
                fontSize = 15.sp, fontWeight = FontWeight.Bold)
            if (!isTransfer && tx.avgPrice.isNotEmpty()) {
                Text(tx.avgPrice, color = AppTextGrey,
                    fontSize = 12.sp, fontWeight = FontWeight.Normal)
            }
        }
    }
}