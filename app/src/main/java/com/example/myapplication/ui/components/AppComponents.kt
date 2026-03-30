package com.example.myapplication.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── Dark-theme colour palette ─────────────────────────────────────────────────
val AppBackground  = Color(0xFF0D1117)
val CardBg         = Color(0xFF1C2028)
val ChipBg         = Color(0xFF2A2D35)
val ChipBgActive   = Color(0xFF1E232C)
val AppGreen       = Color(0xFF39FF14)  // neon green accent (same as PrimaryGreen)
val AppBlue        = Color(0xFF62B6FF)
val BuyGreen       = Color(0xFF00C853)
val SellRed        = Color(0xFFFF3B30)
val WithdrawColor  = Color(0xFFFF6B6B)
val DepositColor   = Color(0xFF4CAF50)
val AppTextWhite   = Color(0xFFFFFFFF)
val AppTextGrey    = Color(0xFF8391A1)
val DividerColor   = Color(0xFF2A2D35)
val CardOutline    = Color(0x20FFFFFF)
val NavSelected    = Color(0xFFE6E9F1)
val BuddyAccent    = Color(0xFF8A67FF)

// ── Top App Bar ───────────────────────────────────────────────────────────────
@Composable
fun AppTopBar(
    name: String = "Preet Panchal",
    balance: String = "\u20B918556",
    onSearchClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(Color(0xFF2A303C))
                .border(1.dp, CardOutline, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Avatar",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(name, color = AppTextWhite, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
            Text(balance, color = AppGreen, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.weight(1f))

        GlassIconButton(
            icon = Icons.Default.Search,
            contentDescription = "Search",
            onClick = onSearchClick
        )

        Spacer(modifier = Modifier.width(8.dp))

        GlassIconButton(
            icon = Icons.Default.Notifications,
            contentDescription = "Notifications",
            onClick = onNotificationClick
        )
    }
}

// ── Bottom Navigation Bar ────────────────────────────────────────────────────
enum class AppTab { HOME, ANALYTICS, BUDDY, STRATEGIES, PROFILE }

@Composable
fun AppBottomBar(
    currentTab: AppTab = AppTab.HOME,
    onTabChange: (AppTab) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF131720))
            .navigationBarsPadding()
    ) {
        HorizontalDivider(color = DividerColor, thickness = 0.5.dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(Icons.Default.Home, "Home",
                currentTab == AppTab.HOME) { onTabChange(AppTab.HOME) }

            BottomNavItem(Icons.Default.BarChart, "Analytics",
                currentTab == AppTab.ANALYTICS) { onTabChange(AppTab.ANALYTICS) }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(
                            color = BuddyAccent.copy(alpha = 0.16f),
                            shape = CircleShape
                        )
                        .padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                        .clip(CircleShape)
                            .background(Color(0xFF1D2432))
                            .border(2.dp, BuddyAccent.copy(alpha = 0.75f), CircleShape)
                        .clickable { onTabChange(AppTab.BUDDY) }
                        .offset(y = (-8).dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                    .background(if (currentTab == AppTab.BUDDY) BuddyAccent else Color.White)
                        )
                    }
                }
                Text(
                    "Ask Buddy.ai",
                    color = if (currentTab == AppTab.BUDDY) BuddyAccent else AppTextGrey,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.offset(y = (-8).dp)
                )
            }

            BottomNavItem(Icons.Default.Stars, "Strategies",
                currentTab == AppTab.STRATEGIES) { onTabChange(AppTab.STRATEGIES) }

            BottomNavItem(Icons.Default.Person, "Profile",
                currentTab == AppTab.PROFILE) { onTabChange(AppTab.PROFILE) }
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Icon(icon, contentDescription = label,
            tint = if (selected) NavSelected else AppTextGrey,
            modifier = Modifier.size(22.dp))
        Spacer(Modifier.height(2.dp))
        Text(label,
            color = if (selected) NavSelected else AppTextGrey,
            fontSize = 10.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun GlassIconButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.06f))
            .border(1.dp, CardOutline, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = AppTextWhite,
            modifier = Modifier.size(20.dp)
        )
    }
}

// ── Deposit / Withdraw row ────────────────────────────────────────────────────
@Composable
fun DepositWithdrawRow(
    onDepositClick: () -> Unit,
    onWithdrawClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onDepositClick,
            modifier = Modifier.weight(1f).height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ChipBg),
            shape = RoundedCornerShape(26.dp),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Icon(Icons.Default.ArrowDownward, null,
                tint = AppTextWhite, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(6.dp))
            Text("Deposit", color = AppTextWhite, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        }
        Button(
            onClick = onWithdrawClick,
            modifier = Modifier.weight(1f).height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(26.dp),
            elevation = ButtonDefaults.buttonElevation(0.dp)
        ) {
            Icon(Icons.Default.ArrowUpward, null,
                tint = Color.Black, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(6.dp))
            Text("Withdraw", color = Color.Black, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
        }
    }
}

// ── Mini Line Chart (Canvas) ──────────────────────────────────────────────────
@Composable
fun MiniLineChart(
    points: List<Float>,
    isPositive: Boolean,
    modifier: Modifier = Modifier
) {
    val lineColor = if (isPositive) BuyGreen else SellRed
    Canvas(modifier = modifier) {
        if (points.size < 2) return@Canvas
        val maxV = points.max()
        val minV = points.min()
        val range = if (maxV == minV) 1f else maxV - minV
        val w = size.width
        val h = size.height
        val stepX = w / (points.size - 1)

        // Filled area path
        val fillPath = Path()
        points.forEachIndexed { i, v ->
            val x = i * stepX
            val y = h - ((v - minV) / range * h * 0.85f) - h * 0.075f
            if (i == 0) fillPath.moveTo(x, y) else fillPath.lineTo(x, y)
        }
        fillPath.lineTo((points.size - 1) * stepX, h)
        fillPath.lineTo(0f, h)
        fillPath.close()

        drawPath(fillPath, brush = Brush.verticalGradient(
            colors = listOf(lineColor.copy(alpha = 0.3f), Color.Transparent),
            startY = 0f, endY = h
        ))

        // Line path
        val linePath = Path()
        points.forEachIndexed { i, v ->
            val x = i * stepX
            val y = h - ((v - minV) / range * h * 0.85f) - h * 0.075f
            if (i == 0) linePath.moveTo(x, y) else linePath.lineTo(x, y)
        }
        drawPath(linePath, color = lineColor, style = Stroke(width = 2.5f, cap = StrokeCap.Round))
    }
}
