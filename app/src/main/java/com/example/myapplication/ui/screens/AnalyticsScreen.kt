package com.example.myapplication.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.AppBackground
import com.example.myapplication.ui.components.AppGreen
import com.example.myapplication.ui.components.AppTextGrey
import com.example.myapplication.ui.components.AppTextWhite
import com.example.myapplication.ui.components.DepositWithdrawRow
import com.example.myapplication.ui.components.DividerColor
import com.example.myapplication.ui.model.PortfolioSlice
import com.example.myapplication.ui.model.PortfolioSliceType
import com.example.myapplication.ui.model.SampleData
import kotlin.math.atan2
import kotlin.math.sqrt

@Composable
fun AnalyticsScreen(
    onDepositClick: () -> Unit = {},
    onWithdrawClick: () -> Unit = {}
) {
    val slices = SampleData.portfolioSlices
    var selectedType by remember { mutableStateOf<PortfolioSliceType?>(null) }

    val visibleSlices = if (selectedType == null) {
        slices
    } else {
        slices.filter { it.type == selectedType }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0F131C), AppBackground, Color(0xFF070A10))
                )
            )
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Portfolio Value",
            color = AppTextGrey,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = SampleData.portfolioValue,
            color = AppTextWhite,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Returns:",
                color = AppTextGrey,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.width(6.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                contentDescription = null,
                tint = AppGreen,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = SampleData.portfolioReturns,
                color = AppGreen,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        DepositWithdrawRow(
            onDepositClick = onDepositClick,
            onWithdrawClick = onWithdrawClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = DividerColor.copy(alpha = 0.65f),
            thickness = 0.8.dp
        )

        Spacer(modifier = Modifier.height(12.dp))

        AllocationDonutSection(
            slices = slices,
            selectedType = selectedType,
            onSliceSelected = { tapped ->
                selectedType = if (selectedType == tapped) null else tapped
            }
        )

        Spacer(modifier = Modifier.height(18.dp))

        visibleSlices.forEachIndexed { index, slice ->
            AllocationValueRow(slice = slice)
            if (index != visibleSlices.lastIndex) {
                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun AllocationDonutSection(
    slices: List<PortfolioSlice>,
    selectedType: PortfolioSliceType?,
    onSliceSelected: (PortfolioSliceType) -> Unit
) {
    val byType = remember(slices) { slices.associateBy { it.type } }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        PortfolioDonutChart(
            slices = slices,
            selectedType = selectedType,
            onSliceTap = onSliceSelected,
            modifier = Modifier.size(250.dp)
        )

        byType[PortfolioSliceType.MFS]?.let { mfs ->
            AllocationTag(
                label = mfs.label,
                percentage = mfs.percentage,
                selected = selectedType == PortfolioSliceType.MFS,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-24).dp, y = 30.dp),
                onClick = { onSliceSelected(PortfolioSliceType.MFS) }
            )
        }

        byType[PortfolioSliceType.STOCKS]?.let { stocks ->
            AllocationTag(
                label = stocks.label,
                percentage = stocks.percentage,
                selected = selectedType == PortfolioSliceType.STOCKS,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = 24.dp, y = 10.dp),
                onClick = { onSliceSelected(PortfolioSliceType.STOCKS) }
            )
        }

        byType[PortfolioSliceType.BONDS]?.let { bonds ->
            AllocationTag(
                label = bonds.label,
                percentage = bonds.percentage,
                selected = selectedType == PortfolioSliceType.BONDS,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset(x = (-16).dp, y = 62.dp),
                onClick = { onSliceSelected(PortfolioSliceType.BONDS) }
            )
        }
    }
}

private data class ArcSpec(
    val slice: PortfolioSlice,
    val startAngle: Float,
    val sweepAngle: Float
)

@Composable
private fun PortfolioDonutChart(
    slices: List<PortfolioSlice>,
    selectedType: PortfolioSliceType?,
    onSliceTap: (PortfolioSliceType) -> Unit,
    modifier: Modifier = Modifier
) {
    val startAngle = -108f
    val gapAngle = 2.6f
    val strokeDp = 35.dp
    val strokePx = with(LocalDensity.current) { strokeDp.toPx() }
    val arcSpecs = remember(slices) { buildArcSpecs(slices, startAngle, gapAngle) }

    Canvas(
        modifier = modifier.pointerInput(slices) {
            detectTapGestures { tapOffset ->
                val center = Offset(size.width / 2f, size.height / 2f)
                val dx = tapOffset.x - center.x
                val dy = tapOffset.y - center.y
                val radius = sqrt(dx * dx + dy * dy)
                val outerRadius = minOf(size.width, size.height) / 2f
                val innerRadius = outerRadius - strokePx

                if (radius < innerRadius || radius > outerRadius) {
                    return@detectTapGestures
                }

                val tapAngle = ((Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())) + 360.0) % 360.0).toFloat()
                val hit = arcSpecs.firstOrNull { spec ->
                    isAngleInsideArc(tapAngle, spec.startAngle, spec.sweepAngle)
                }

                hit?.let { onSliceTap(it.slice.type) }
            }
        }
    ) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val ringInset = strokePx / 2f

        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF2F3350).copy(alpha = 0.35f), Color.Transparent),
                center = center,
                radius = minOf(size.width, size.height) * 0.58f
            ),
            radius = minOf(size.width, size.height) * 0.48f,
            center = center
        )

        drawArc(
            color = Color(0xFF161B25),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(ringInset, ringInset),
            size = androidx.compose.ui.geometry.Size(
                width = size.width - strokePx,
                height = size.height - strokePx
            ),
            style = Stroke(width = strokePx, cap = StrokeCap.Round)
        )

        arcSpecs.forEach { spec ->
            val tones = sliceTones(spec.slice.type)
            val color = when {
                selectedType == null -> tones.base
                selectedType == spec.slice.type -> tones.active
                else -> tones.base.copy(alpha = 0.38f)
            }

            drawArc(
                color = color,
                startAngle = spec.startAngle,
                sweepAngle = spec.sweepAngle,
                useCenter = false,
                topLeft = Offset(ringInset, ringInset),
                size = androidx.compose.ui.geometry.Size(
                    width = size.width - strokePx,
                    height = size.height - strokePx
                ),
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }

        drawCircle(
            color = Color(0xFF06090F),
            radius = (minOf(size.width, size.height) - (strokePx * 2.05f)) / 2f,
            center = center
        )
        drawCircle(
            color = Color.Black.copy(alpha = 0.16f),
            radius = (minOf(size.width, size.height) - (strokePx * 2.3f)) / 2f,
            center = center
        )
    }
}

private fun buildArcSpecs(
    slices: List<PortfolioSlice>,
    startAngle: Float,
    gapAngle: Float
): List<ArcSpec> {
    val total = slices.sumOf { it.percentage }.toFloat().coerceAtLeast(1f)
    var currentStart = startAngle

    return slices.map { slice ->
        val rawSweep = 360f * (slice.percentage / total)
        val adjustedSweep = (rawSweep - gapAngle).coerceAtLeast(2f)
        val spec = ArcSpec(
            slice = slice,
            startAngle = currentStart + (gapAngle / 2f),
            sweepAngle = adjustedSweep
        )
        currentStart += rawSweep
        spec
    }
}

private fun isAngleInsideArc(angle: Float, startAngle: Float, sweepAngle: Float): Boolean {
    val normalizedStart = ((startAngle % 360f) + 360f) % 360f
    val normalizedEnd = (normalizedStart + sweepAngle) % 360f
    val normalizedAngle = ((angle % 360f) + 360f) % 360f

    return if (normalizedStart <= normalizedEnd) {
        normalizedAngle in normalizedStart..normalizedEnd
    } else {
        normalizedAngle >= normalizedStart || normalizedAngle <= normalizedEnd
    }
}

private data class SliceTones(
    val base: Color,
    val active: Color
)

private fun sliceTones(type: PortfolioSliceType): SliceTones = when (type) {
    PortfolioSliceType.STOCKS -> SliceTones(base = Color(0xFF7E5A23), active = Color(0xFFCE8C2A))
    PortfolioSliceType.MFS -> SliceTones(base = Color(0xFF4C4CA0), active = Color(0xFF615FC2))
    PortfolioSliceType.BONDS -> SliceTones(base = Color(0xFF8D101A), active = Color(0xFFFF1A24))
}

@Composable
private fun AllocationTag(
    label: String,
    percentage: Int,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(10.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .background(Color(0xFF2B303A))
            .border(
                width = if (selected) 1.dp else 0.dp,
                color = if (selected) Color.White.copy(alpha = 0.92f) else Color.Transparent,
                shape = shape
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 11.dp, vertical = 8.dp)
    ) {
        Text(
            text = "$label\n$percentage%",
            color = if (selected) AppTextWhite else AppTextGrey,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AllocationValueRow(slice: PortfolioSlice) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = slice.label,
                color = AppTextWhite,
                fontSize = 31.sp,
                fontWeight = FontWeight.SemiBold
            )

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = slice.amount,
                    color = AppTextWhite,
                    fontSize = 31.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = slice.returns,
                    color = AppGreen,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
