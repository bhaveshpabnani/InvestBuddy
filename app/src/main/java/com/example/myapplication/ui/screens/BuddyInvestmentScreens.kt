package com.example.myapplication.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.AppBackground
import com.example.myapplication.ui.components.AppTextGrey
import com.example.myapplication.ui.components.AppTextWhite
import com.example.myapplication.ui.model.BuddyAssetClass
import com.example.myapplication.ui.model.BuddyBondDetail
import com.example.myapplication.ui.model.BuddyEquityDetail
import com.example.myapplication.ui.model.BuddyMarketData
import com.example.myapplication.ui.model.BuddyMarketItem
import com.example.myapplication.ui.model.BuddyTrend

private val BuddySurface = Color(0xFF2B2F37)
private val BuddySurfaceMuted = Color(0xFF2A2E36)
private val BuddyPositive = Color(0xFF00D845)
private val BuddyNegative = Color(0xFFFF3D48)
private val BuddyNeutral = Color(0xFF8A8F9B)
private val BuddyViolet = Color(0xFF8A67FF)
private val SearchSurface = Color(0xFFF2F3F5)

@Composable
fun AskBuddyInvestmentScreen() {
    var selectedCategory by rememberSaveable { mutableStateOf(BuddyAssetClass.STOCKS) }
    var selectedAssetId by rememberSaveable { mutableStateOf<String?>(null) }
    var query by rememberSaveable { mutableStateOf("") }

    val selectedDetail = remember(selectedAssetId) {
        selectedAssetId?.let { BuddyMarketData.detailFor(it) }
    }

    BackHandler(enabled = selectedDetail != null) {
        selectedAssetId = null
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        if (selectedDetail == null) {
            val visibleItems = remember(selectedCategory, query) {
                BuddyMarketData.itemsFor(selectedCategory).filter { item ->
                    query.isBlank() || item.name.contains(query, ignoreCase = true)
                }
            }

            BuddyMarketListScreen(
                category = selectedCategory,
                query = query,
                visibleItems = visibleItems,
                onCategorySelected = {
                    selectedCategory = it
                    query = ""
                },
                onQueryChanged = { query = it },
                onAssetClick = { selectedAssetId = it.id },
                onAiSearchClick = {}
            )
        } else {
            when (selectedDetail) {
                is BuddyEquityDetail -> BuddyEquityDetailScreen(
                    detail = selectedDetail,
                    isFavorite = BuddyMarketData.isWatchlisted(selectedDetail.id),
                    onBackClick = { selectedAssetId = null },
                    onFavoriteClick = { BuddyMarketData.toggleWatchlist(selectedDetail.id) }
                )

                is BuddyBondDetail -> BuddyBondDetailScreen(
                    detail = selectedDetail,
                    isFavorite = BuddyMarketData.isWatchlisted(selectedDetail.id),
                    onBackClick = { selectedAssetId = null },
                    onFavoriteClick = { BuddyMarketData.toggleWatchlist(selectedDetail.id) }
                )
            }
        }
    }
}

@Composable
private fun BuddyMarketListScreen(
    category: BuddyAssetClass,
    query: String,
    visibleItems: List<BuddyMarketItem>,
    onCategorySelected: (BuddyAssetClass) -> Unit,
    onQueryChanged: (String) -> Unit,
    onAssetClick: (BuddyMarketItem) -> Unit,
    onAiSearchClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryChip(
                label = "Stocks",
                selected = category == BuddyAssetClass.STOCKS,
                modifier = Modifier.weight(1f),
                onClick = { onCategorySelected(BuddyAssetClass.STOCKS) }
            )
            CategoryChip(
                label = "MFs",
                selected = category == BuddyAssetClass.MFS,
                modifier = Modifier.weight(1f),
                onClick = { onCategorySelected(BuddyAssetClass.MFS) }
            )
            CategoryChip(
                label = "Bonds",
                selected = category == BuddyAssetClass.BONDS,
                modifier = Modifier.weight(1f),
                onClick = { onCategorySelected(BuddyAssetClass.BONDS) }
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        TextField(
            value = query,
            onValueChange = onQueryChanged,
            placeholder = {
                Text(
                    text = "Search instruments",
                    color = Color(0xFF747A87),
                    fontSize = 14.sp
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF2D333F),
                    modifier = Modifier.size(26.dp)
                )
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = SearchSurface,
                unfocusedContainerColor = SearchSurface,
                disabledContainerColor = SearchSurface,
                focusedTextColor = Color(0xFF1E2430),
                unfocusedTextColor = Color(0xFF1E2430),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color(0xFF1E2430)
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Recently Viewed",
            color = AppTextWhite,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(18.dp))

        visibleItems.forEachIndexed { index, item ->
            MarketListRow(item = item, onClick = { onAssetClick(item) })
            if (index != visibleItems.lastIndex) {
                Spacer(modifier = Modifier.height(22.dp))
            }
        }

        if (visibleItems.isEmpty()) {
            Text(
                text = "No instruments found",
                color = AppTextGrey,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = SearchSurface,
            tonalElevation = 0.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onAiSearchClick)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "Search Using AI for your Profile",
                    color = Color(0xFF10141E),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun CategoryChip(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        color = BuddySurface,
        shape = RoundedCornerShape(24.dp),
        border = if (selected) {
            androidx.compose.foundation.BorderStroke(1.2.dp, Color(0xFFE9EBF3))
        } else {
            null
        },
        modifier = modifier
            .height(54.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                color = if (selected) AppTextWhite else BuddyNeutral,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun MarketListRow(
    item: BuddyMarketItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name,
            color = AppTextWhite,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = item.primaryValue,
                color = AppTextWhite,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )

            item.secondaryValue?.let { delta ->
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = delta,
                    color = when (item.trend) {
                        BuddyTrend.POSITIVE -> BuddyPositive
                        BuddyTrend.NEGATIVE -> BuddyNegative
                        BuddyTrend.NEUTRAL -> BuddyNeutral
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun BuddyEquityDetailScreen(
    detail: BuddyEquityDetail,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    var selectedRange by rememberSaveable(detail.id) {
        mutableStateOf(detail.ranges.firstOrNull()?.label.orEmpty())
    }

    val selectedPoints = remember(detail.ranges, selectedRange) {
        detail.ranges.firstOrNull { it.label == selectedRange }?.points
            ?: detail.ranges.firstOrNull()?.points
            ?: emptyList()
    }

    val headlineTrend = when {
        detail.headlineChange.startsWith("+") -> BuddyTrend.POSITIVE
        detail.headlineChange.startsWith("-") -> BuddyTrend.NEGATIVE
        else -> BuddyTrend.NEUTRAL
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        DetailHeader(
            title = detail.name,
            isFavorite = isFavorite,
            onBackClick = onBackClick,
            onFavoriteClick = onFavoriteClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = detail.headlineValue,
            color = AppTextWhite,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = detail.headlineChange,
                color = trendColor(headlineTrend),
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = selectedRange,
                color = BuddyNeutral,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        EquityChart(
            points = selectedPoints,
            trend = headlineTrend,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            detail.ranges.forEach { range ->
                RangeChip(
                    label = range.label,
                    selected = selectedRange == range.label,
                    onClick = { selectedRange = range.label }
                )
            }
        }

        Spacer(modifier = Modifier.height(26.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = detail.holdingTitle,
                    color = AppTextWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = detail.holdingCaption,
                    color = BuddyNeutral,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = detail.holdingValue,
                    color = AppTextWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = detail.holdingValueChange,
                    color = trendColor(detail.holdingValueTrend),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Ratings out of 10",
            color = AppTextWhite,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            detail.ratings.forEachIndexed { index, rating ->
                RatingCard(
                    label = rating.label,
                    score = rating.score,
                    highlighted = index == 0
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = detail.aiSuggestion,
            color = BuddyViolet,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 23.sp
        )

        Spacer(modifier = Modifier.height(34.dp))

        ActionButtonsRow(
            primaryAction = detail.primaryAction,
            secondaryAction = detail.secondaryAction
        )

        Spacer(modifier = Modifier.height(22.dp))
    }
}

@Composable
private fun BuddyBondDetailScreen(
    detail: BuddyBondDetail,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 18.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        DetailHeader(
            title = detail.name,
            isFavorite = isFavorite,
            onBackClick = onBackClick,
            onFavoriteClick = onFavoriteClick
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = detail.headlineValue,
            color = AppTextWhite,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(36.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column {
                Text(
                    text = detail.unitsValue,
                    color = AppTextWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = detail.avgPrice,
                    color = BuddyNeutral,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Text(
                text = detail.holdingValue,
                color = AppTextWhite,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Ratings out of 10",
            color = AppTextWhite,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            detail.ratings.forEachIndexed { index, rating ->
                RatingCard(
                    label = rating.label,
                    score = rating.score,
                    highlighted = index == 0
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = detail.aiSuggestion,
            color = BuddyViolet,
            fontSize = 17.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 23.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        ActionButtonsRow(
            primaryAction = detail.primaryAction,
            secondaryAction = detail.secondaryAction
        )

        Spacer(modifier = Modifier.height(22.dp))
    }
}

@Composable
private fun DetailHeader(
    title: String,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape)
                .background(BuddySurfaceMuted)
                .clickable(onClick = onBackClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFFE7EBF4),
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = title,
            color = AppTextWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f)
        )

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(BuddySurfaceMuted)
                .clickable(onClick = onFavoriteClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (isFavorite) BuddyViolet else Color(0xFFE7EBF4),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun EquityChart(
    points: List<Float>,
    trend: BuddyTrend,
    modifier: Modifier = Modifier
) {
    val lineColor = when (trend) {
        BuddyTrend.POSITIVE -> BuddyPositive
        BuddyTrend.NEGATIVE -> BuddyNegative
        BuddyTrend.NEUTRAL -> Color(0xFFE6E9F2)
    }

    Canvas(modifier = modifier) {
        if (points.size < 2) {
            return@Canvas
        }

        val maxPoint = points.max()
        val minPoint = points.min()
        val range = if (maxPoint == minPoint) 1f else maxPoint - minPoint
        val stepX = size.width / (points.size - 1)

        val path = Path()
        points.forEachIndexed { index, point ->
            val x = index * stepX
            val y = size.height - ((point - minPoint) / range * size.height * 0.84f) - (size.height * 0.08f)
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                path.lineTo(x, y)
            }
        }

        drawPath(
            path = path,
            color = lineColor.copy(alpha = 0.32f),
            style = Stroke(width = 9f, cap = StrokeCap.Round)
        )
        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 4.5f, cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun RangeChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(22.dp),
        color = BuddySurface,
        border = if (selected) {
            androidx.compose.foundation.BorderStroke(1.2.dp, Color(0xFFE8EAF2))
        } else {
            null
        },
        modifier = Modifier
            .clip(RoundedCornerShape(22.dp))
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 28.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                color = if (selected) AppTextWhite else BuddyNeutral,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun RatingCard(
    label: String,
    score: Int,
    highlighted: Boolean
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = BuddySurface,
        border = if (highlighted) {
            androidx.compose.foundation.BorderStroke(1.2.dp, Color(0xFFE8EAF2))
        } else {
            null
        },
        modifier = Modifier
            .width(122.dp)
            .height(120.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                color = if (highlighted) Color(0xFFEDEFF7) else BuddyNeutral,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = score.toString(),
                color = if (highlighted) AppTextWhite else Color(0xFF717684),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ActionButtonsRow(
    primaryAction: String,
    secondaryAction: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            shape = RoundedCornerShape(34.dp),
            color = BuddySurface,
            modifier = Modifier
                .weight(1f)
                .height(68.dp)
                .clip(RoundedCornerShape(34.dp))
                .clickable { }
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = primaryAction,
                    color = AppTextWhite,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Surface(
            shape = RoundedCornerShape(34.dp),
            color = SearchSurface,
            modifier = Modifier
                .weight(1f)
                .height(68.dp)
                .clip(RoundedCornerShape(34.dp))
                .clickable { }
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = secondaryAction,
                    color = Color(0xFF121723),
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

private fun trendColor(trend: BuddyTrend): Color = when (trend) {
    BuddyTrend.POSITIVE -> BuddyPositive
    BuddyTrend.NEGATIVE -> BuddyNegative
    BuddyTrend.NEUTRAL -> BuddyNeutral
}