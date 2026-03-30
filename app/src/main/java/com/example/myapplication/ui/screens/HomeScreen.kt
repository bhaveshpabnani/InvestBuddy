package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.*
import com.example.myapplication.ui.model.BuddyMarketData
import com.example.myapplication.ui.model.BuddyTrend
import com.example.myapplication.ui.model.SampleData
import com.example.myapplication.ui.model.StockItem

@Composable
fun HomeScreen(
    onDepositClick: () -> Unit = {},
    onWithdrawClick: () -> Unit = {},
    onSeeAllViewedClick: () -> Unit = {},
    onSeeAllWatchlistClick: () -> Unit = {}
) {
    val buddyWatchlist = buddyWatchlistAsStocks()
    val watchlistStocks = (SampleData.watchlistStocks + buddyWatchlist).distinctBy { "${it.ticker}-${it.company}" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ── Portfolio Value ──────────────────────────────────────────────────
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Portfolio Value",
            color = AppTextGrey,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "₹1,62,120",
            color = AppTextWhite,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.AutoMirrored.Filled.TrendingUp,
                contentDescription = null,
                tint = AppGreen,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = "Returns: +62.12%",
                color = AppGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

        // ── Deposit / Withdraw ───────────────────────────────────────────────
        DepositWithdrawRow(
            onDepositClick = onDepositClick,
            onWithdrawClick = onWithdrawClick
        )

        Spacer(modifier = Modifier.height(36.dp))

        // ── You Viewed ───────────────────────────────────────────────────────
        SectionHeader(title = "You Viewed", onSeeAllClick = onSeeAllViewedClick)

        Spacer(modifier = Modifier.height(14.dp))

        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(SampleData.viewedStocks) { stock ->
                StockCard(stock = stock, onClick = {})
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        // ── Watchlist ────────────────────────────────────────────────────────
        SectionHeader(title = "Watchlist", onSeeAllClick = onSeeAllWatchlistClick)

        Spacer(modifier = Modifier.height(14.dp))

        if (watchlistStocks.isEmpty()) {
            Text(
                text = "No stocks in your watchlist yet",
                color = AppTextGrey,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        } else {
            watchlistStocks.forEach { stock ->
                WatchlistRow(stock = stock, onClick = {})
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    color = DividerColor,
                    thickness = 0.5.dp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// ── Section header row ────────────────────────────────────────────────────────
@Composable
private fun SectionHeader(title: String, onSeeAllClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = AppTextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(
            "See all",
            color = AppTextGrey,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.clickable { onSeeAllClick() }
        )
    }
}

// ── Stock Card (grid layout) ──────────────────────────────────────────────────
@Composable
fun StockCard(stock: StockItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .widthIn(min = 148.dp, max = 172.dp)
            .heightIn(min = 146.dp, max = 160.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardBg)
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Ticker badge + company
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(ChipBg),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stock.ticker.take(2),
                        color = AppTextWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(stock.ticker, color = AppTextWhite,
                        fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text(stock.company, color = AppTextGrey, fontSize = 10.sp,
                        maxLines = 1)
                }
            }

            Spacer(Modifier.weight(1f))

            // Mini chart
            MiniLineChart(
                points = stock.chartPoints,
                isPositive = stock.isPositive,
                modifier = Modifier.fillMaxWidth().height(40.dp)
            )

            Spacer(Modifier.height(8.dp))

            // Price and change
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(stock.price, color = AppTextWhite,
                    fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                Text(
                    stock.change,
                    color = if (stock.isPositive) BuyGreen else SellRed,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

// ── Watchlist Row ─────────────────────────────────────────────────────────────
@Composable
fun WatchlistRow(stock: StockItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(ChipBg),
            contentAlignment = Alignment.Center
        ) {
            Text(stock.ticker.take(2), color = AppTextWhite,
                fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(stock.ticker, color = AppTextWhite,
                fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Text(stock.company, color = AppTextGrey, fontSize = 12.sp)
        }

        // Chart
        MiniLineChart(
            points = stock.chartPoints,
            isPositive = stock.isPositive,
            modifier = Modifier.width(64.dp).height(30.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column(horizontalAlignment = Alignment.End) {
            Text(stock.price, color = AppTextWhite,
                fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
            Text(
                stock.change,
                color = if (stock.isPositive) BuyGreen else SellRed,
                fontSize = 11.sp, fontWeight = FontWeight.Medium
            )
        }
    }
}

private fun buddyWatchlistAsStocks(): List<StockItem> {
    return BuddyMarketData.watchlistItems().map { item ->
        val initials = item.name
            .split(" ")
            .mapNotNull { token -> token.firstOrNull()?.toString() }
            .joinToString("")
            .uppercase()
            .take(5)
            .ifBlank { item.name.take(4).uppercase() }

        val isPositive = item.trend != BuddyTrend.NEGATIVE
        val chart = if (isPositive) {
            listOf(38f, 42f, 45f, 48f, 50f, 54f, 56f)
        } else {
            listOf(58f, 56f, 54f, 51f, 48f, 45f, 42f)
        }

        StockItem(
            ticker = initials,
            company = item.name,
            price = item.primaryValue,
            change = item.secondaryValue ?: if (isPositive) "+0.00%" else "-0.00%",
            isPositive = isPositive,
            chartPoints = chart
        )
    }
}
