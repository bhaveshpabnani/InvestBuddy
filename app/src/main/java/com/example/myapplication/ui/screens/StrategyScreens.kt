@file:OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)

package com.example.myapplication.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.AppBackground
import com.example.myapplication.ui.components.AppGreen
import com.example.myapplication.ui.components.AppTextGrey
import com.example.myapplication.ui.components.AppTextWhite
import com.example.myapplication.ui.components.CardBg
import com.example.myapplication.ui.components.DividerColor
import com.example.myapplication.ui.model.SampleData
import com.example.myapplication.ui.model.StrategyDraftSummary
import com.example.myapplication.ui.model.StrategyIdeaCard
import com.example.myapplication.ui.model.StrategyKind

@Composable
fun AskBuddyHubScreen(
    onStrategySelected: (StrategyKind) -> Unit,
    onSavedClick: () -> Unit
) {
    val ideas = SampleData.strategyIdeas
    val compactWidth = androidx.compose.ui.platform.LocalConfiguration.current.screenWidthDp < 390

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Use AI Intelligence\nfor Investment",
            color = AppTextWhite,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Choose a strategy framework and convert market logic into deployable alerts or orders.",
            color = AppTextGrey,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            maxItemsInEachRow = if (compactWidth) 1 else 2
        ) {
            ideas.forEach { idea ->
                StrategyIdeaCard(
                    idea = idea,
                    modifier = Modifier.fillMaxWidth(if (compactWidth) 1f else 0.48f),
                    onClick = {
                        if (!idea.isLocked) onStrategySelected(idea.kind)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        HubPrimaryPill(text = "Saved Strategies", onClick = onSavedClick)
        Spacer(modifier = Modifier.height(28.dp))
    }
}

@Composable
fun SavedStrategiesScreen(
    onCreateNewClick: () -> Unit,
    onFileStrategyClick: () -> Unit,
    onStrategySelected: (StrategyKind) -> Unit
) {
    val savedStrategies = SampleData.savedStrategies

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Saved Strategies",
            color = AppTextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Monitor the strategy logic you already configured and jump back into editing when needed.",
            color = AppTextGrey,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(20.dp))
        HubPrimaryPill(text = "Create New Strategy", onClick = onCreateNewClick)
        Spacer(modifier = Modifier.height(12.dp))
        HubPrimaryPill(text = "Strategy Filing", onClick = onFileStrategyClick)
        Spacer(modifier = Modifier.height(22.dp))

        savedStrategies.forEachIndexed { index, strategy ->
            SavedStrategyCard(
                summary = strategy,
                onClick = { onStrategySelected(strategy.kind) }
            )
            if (index != savedStrategies.lastIndex) {
                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        Spacer(modifier = Modifier.height(28.dp))
    }
}

@Composable
fun StrategyDetailScreen(
    kind: StrategyKind,
    onBackClick: () -> Unit,
    onSavedClick: () -> Unit,
    onFileStrategyClick: () -> Unit,
    onAlertClick: () -> Unit = {},
    onOrderClick: () -> Unit = {}
) {
    val config = strategyFormConfig(kind)
    val compactWidth = androidx.compose.ui.platform.LocalConfiguration.current.screenWidthDp < 390

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StrategyTopChip(
                text = "Back to Builder",
                leadingIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = AppTextWhite,
                        modifier = Modifier.size(16.dp)
                    )
                },
                onClick = onBackClick
            )
            StrategyTopChip(text = "Saved Strategies", onClick = onSavedClick)
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = config.title,
            color = AppTextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = config.subtitle,
            color = AppTextGrey,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )

        Spacer(modifier = Modifier.height(22.dp))
        StrategyField(label = config.nameLabel, value = AnnotatedString(config.nameValue))
        Spacer(modifier = Modifier.height(16.dp))
        StrategyField(label = config.primaryLabel, value = config.primaryValue, showSearchIcon = true)

        config.secondaryLabel?.let { label ->
            Spacer(modifier = Modifier.height(16.dp))
            StrategyField(
                label = label,
                value = config.secondaryValue ?: AnnotatedString(""),
                showSearchIcon = true
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        StrategyField(
            label = config.stockLabel,
            value = AnnotatedString(config.stockValue),
            showSearchIcon = true
        )

        Spacer(modifier = Modifier.height(18.dp))

        if (compactWidth) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                StrategyCompactField(label = "Units", value = config.unitsValue)
                StrategyCompactField(label = "Amount", value = config.amountValue)
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                StrategyCompactField(label = "Units", value = config.unitsValue, modifier = Modifier.weight(1f))
                StrategyCompactField(label = "Amount", value = config.amountValue, modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StrategyActionButton(
                text = "Alert",
                modifier = Modifier.fillMaxWidth(if (compactWidth) 1f else 0.32f),
                onClick = onAlertClick
            )
            StrategyActionButton(
                text = "Order",
                modifier = Modifier.fillMaxWidth(if (compactWidth) 1f else 0.32f),
                onClick = onOrderClick
            )
            StrategyActionButton(
                text = "File",
                modifier = Modifier.fillMaxWidth(if (compactWidth) 1f else 0.32f),
                onClick = onFileStrategyClick
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = config.investText,
            color = AppGreen,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.End)
        )

        Spacer(modifier = Modifier.height(18.dp))

        Surface(
            shape = RoundedCornerShape(24.dp),
            color = CardBg.copy(alpha = 0.72f),
            tonalElevation = 0.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(18.dp)) {
                Text(
                    text = "AI Assist",
                    color = strategyAccent(kind),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = config.footerNote,
                    color = AppTextWhite,
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))
    }
}

@Composable
private fun StrategyIdeaCard(
    idea: StrategyIdeaCard,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val accent = strategyAccent(idea.kind)

    Surface(
        modifier = modifier
            .heightIn(min = 194.dp, max = 224.dp)
            .clip(RoundedCornerShape(28.dp))
            .clickable(enabled = !idea.isLocked, onClick = onClick),
        shape = RoundedCornerShape(28.dp),
        color = CardBg.copy(alpha = 0.96f),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (idea.isLocked) DividerColor else accent.copy(alpha = 0.28f)
        ),
        tonalElevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF121722))
                .padding(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF11141A))
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.06f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(12.dp)
            ) {
                StrategyPreviewCanvas(
                    kind = idea.kind,
                    accent = accent,
                    modifier = Modifier.fillMaxSize()
                )

                if (idea.isLocked) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.28f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.10f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = idea.title,
                color = AppTextWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = idea.subtitle,
                color = accent,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = idea.supportingText,
                color = AppTextGrey,
                fontSize = 12.sp,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun SavedStrategyCard(
    summary: StrategyDraftSummary,
    onClick: () -> Unit
) {
    val accent = strategyAccent(summary.kind)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(26.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(26.dp),
        color = CardBg.copy(alpha = 0.94f),
        border = androidx.compose.foundation.BorderStroke(1.dp, accent.copy(alpha = 0.22f)),
        tonalElevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StrategyStatusBadge(
                    text = summary.kind.name.lowercase().replaceFirstChar { it.uppercase() },
                    background = accent.copy(alpha = 0.16f),
                    contentColor = accent
                )
                Spacer(modifier = Modifier.weight(1f))
                StrategyStatusBadge(
                    text = summary.status,
                    background = Color.White.copy(alpha = 0.06f),
                    contentColor = AppTextWhite
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = summary.title,
                color = AppTextWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = summary.instrument,
                color = AppTextGrey,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = DividerColor, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Trigger", color = AppTextGrey, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = summary.trigger,
                        color = AppTextWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Capital", color = AppTextGrey, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = summary.capital,
                        color = accent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun StrategyField(
    label: String,
    value: AnnotatedString,
    showSearchIcon: Boolean = false
) {
    val compactWidth = androidx.compose.ui.platform.LocalConfiguration.current.screenWidthDp < 380

    Column {
        Text(
            text = label,
            color = AppTextWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Color(0xFF171D27),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.08f)),
            tonalElevation = 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    color = AppTextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )

                if (showSearchIcon) {
                    Box(
                        modifier = Modifier
                            .size(if (compactWidth) 24.dp else 30.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF272F3C)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = Color(0xFFB6C0CF),
                            modifier = Modifier.size(if (compactWidth) 14.dp else 18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StrategyCompactField(
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

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            color = Color(0xFF171D27),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.08f)),
            tonalElevation = 0.dp
        ) {
            Text(
                text = value,
                color = AppTextWhite,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 14.dp)
            )
        }
    }
}

@Composable
private fun StrategyActionButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val isPrimary = text.equals("File", ignoreCase = true)

    Box(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(31.dp))
            .background(if (isPrimary) Color.White else Color(0xFF1A2230))
            .border(
                width = 1.dp,
                color = if (isPrimary) Color.White else Color.White.copy(alpha = 0.14f),
                shape = RoundedCornerShape(31.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isPrimary) Color(0xFF151B26) else Color(0xFFE0E7F4),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun StrategyTopChip(
    text: String,
    leadingIcon: (@Composable () -> Unit)? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.06f))
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.08f),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon?.invoke()
        if (leadingIcon != null) {
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            color = AppTextWhite,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun HubPrimaryPill(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(31.dp))
            .background(Color(0xFF1A2230))
            .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(31.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = AppTextWhite,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun StrategyStatusBadge(
    text: String,
    background: Color,
    contentColor: Color
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(14.dp))
            .background(background)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = contentColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun StrategyFilingScreen(
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit
) {
    var strategyName by androidx.compose.runtime.saveable.rememberSaveable { androidx.compose.runtime.mutableStateOf("") }
    var filingType by androidx.compose.runtime.saveable.rememberSaveable { androidx.compose.runtime.mutableStateOf("Intraday") }
    var filingCategory by androidx.compose.runtime.saveable.rememberSaveable { androidx.compose.runtime.mutableStateOf("Speculation") }
    var brokerName by androidx.compose.runtime.saveable.rememberSaveable { androidx.compose.runtime.mutableStateOf("") }
    var maxConcurrentOrders by androidx.compose.runtime.saveable.rememberSaveable { androidx.compose.runtime.mutableStateOf("") }
    var dailyLossLimit by androidx.compose.runtime.saveable.rememberSaveable { androidx.compose.runtime.mutableStateOf("") }
    var maxPositionSize by androidx.compose.runtime.saveable.rememberSaveable { androidx.compose.runtime.mutableStateOf("") }
    var notes by androidx.compose.runtime.saveable.rememberSaveable { androidx.compose.runtime.mutableStateOf("") }
    var autoRenew by androidx.compose.runtime.saveable.rememberSaveable { androidx.compose.runtime.mutableStateOf(true) }

    val compactWidth = androidx.compose.ui.platform.LocalConfiguration.current.screenWidthDp < 390
    val canSubmit = strategyName.isNotBlank() &&
        brokerName.isNotBlank() &&
        maxConcurrentOrders.toIntOrNull() != null &&
        dailyLossLimit.isNotBlank() &&
        maxPositionSize.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        StrategyTopChip(
            text = "Back to Strategies",
            leadingIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = AppTextWhite,
                    modifier = Modifier.size(16.dp)
                )
            },
            onClick = onBackClick
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Strategy Filing",
            color = AppTextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Submit your strategy setup with compliance and risk details before rollout.",
            color = AppTextGrey,
            fontSize = 13.sp,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        FilingTextField(
            label = "Strategy Name",
            value = strategyName,
            placeholder = "Momentum Intraday v2",
            onValueChange = { strategyName = it }
        )

        Spacer(modifier = Modifier.height(14.dp))

        FilingDropdownField(
            label = "Filing Type",
            selected = filingType,
            options = listOf("Intraday", "Positional", "Swing"),
            onSelected = { filingType = it }
        )

        Spacer(modifier = Modifier.height(14.dp))

        FilingDropdownField(
            label = "Filing Category",
            selected = filingCategory,
            options = listOf("Speculation", "Hedging", "Arbitrage"),
            onSelected = { filingCategory = it }
        )

        Spacer(modifier = Modifier.height(14.dp))

        FilingTextField(
            label = "Broker Name",
            value = brokerName,
            placeholder = "ICICI Direct",
            onValueChange = { brokerName = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (compactWidth) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                FilingTextField(
                    label = "Max Concurrent Orders",
                    value = maxConcurrentOrders,
                    placeholder = "5",
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                    onValueChange = { maxConcurrentOrders = it.filter(Char::isDigit) }
                )
                FilingTextField(
                    label = "Daily Loss Limit",
                    value = dailyLossLimit,
                    placeholder = "-5000",
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                    onValueChange = { dailyLossLimit = it.filter { c -> c.isDigit() || c == '-' } }
                )
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilingTextField(
                    label = "Max Concurrent Orders",
                    value = maxConcurrentOrders,
                    placeholder = "5",
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                    onValueChange = { maxConcurrentOrders = it.filter(Char::isDigit) },
                    modifier = Modifier.weight(1f)
                )
                FilingTextField(
                    label = "Daily Loss Limit",
                    value = dailyLossLimit,
                    placeholder = "-5000",
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
                    onValueChange = { dailyLossLimit = it.filter { c -> c.isDigit() || c == '-' } },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        FilingTextField(
            label = "Max Position Size",
            value = maxPositionSize,
            placeholder = "100000",
            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number,
            onValueChange = { maxPositionSize = it.filter(Char::isDigit) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        FilingTextField(
            label = "Reviewer Notes",
            value = notes,
            placeholder = "Optional context for compliance reviewer",
            maxLines = 3,
            onValueChange = { notes = it }
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Documents",
            color = AppTextWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(10.dp))

        FilingDocumentRow(name = "Backtest_Q1_2026.pdf", status = "Verified")
        Spacer(modifier = Modifier.height(8.dp))
        FilingDocumentRow(name = "Strategy_Logic.pdf", status = "Pending")

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFF171D27))
                .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(14.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Auto renew after expiry",
                color = AppTextWhite,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
            androidx.compose.material3.Switch(
                checked = autoRenew,
                onCheckedChange = { autoRenew = it },
                colors = androidx.compose.material3.SwitchDefaults.colors(
                    checkedThumbColor = Color(0xFF0C121A),
                    checkedTrackColor = AppGreen,
                    uncheckedThumbColor = AppTextGrey,
                    uncheckedTrackColor = Color(0xFF2B3342)
                )
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        com.example.myapplication.ui.components.PrimaryButton(
            text = "Submit Filing",
            enabled = canSubmit,
            onClick = onSubmitClick
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
private fun FilingDropdownField(
    label: String,
    selected: String,
    options: List<String>,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = AppTextWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        androidx.compose.material3.ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            androidx.compose.material3.OutlinedTextField(
                value = selected,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AppGreen,
                    unfocusedBorderColor = Color.White.copy(alpha = 0.12f),
                    focusedContainerColor = Color(0xFF171D27),
                    unfocusedContainerColor = Color(0xFF171D27),
                    focusedTextColor = AppTextWhite,
                    unfocusedTextColor = AppTextWhite
                ),
                shape = RoundedCornerShape(14.dp),
                singleLine = true
            )

            androidx.compose.material3.DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = Color(0xFF171D27)
            ) {
                options.forEach { item ->
                    androidx.compose.material3.DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                color = AppTextWhite,
                                fontSize = 14.sp
                            )
                        },
                        onClick = {
                            onSelected(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FilingTextField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: androidx.compose.ui.text.input.KeyboardType = androidx.compose.ui.text.input.KeyboardType.Text,
    maxLines: Int = 1
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = AppTextWhite,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        androidx.compose.material3.OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = AppTextGrey,
                    fontSize = 13.sp
                )
            },
            modifier = Modifier.fillMaxWidth(),
            maxLines = maxLines,
            singleLine = maxLines == 1,
            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = if (maxLines == 1) androidx.compose.ui.text.input.ImeAction.Next else androidx.compose.ui.text.input.ImeAction.Default
            ),
            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppGreen,
                unfocusedBorderColor = Color.White.copy(alpha = 0.12f),
                focusedContainerColor = Color(0xFF171D27),
                unfocusedContainerColor = Color(0xFF171D27),
                focusedTextColor = AppTextWhite,
                unfocusedTextColor = AppTextWhite
            ),
            shape = RoundedCornerShape(14.dp)
        )
    }
}

@Composable
private fun FilingDocumentRow(
    name: String,
    status: String
) {
    val positive = status.equals("Verified", ignoreCase = true)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF171D27))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = if (positive) Icons.Default.Check else Icons.Default.Lock,
            contentDescription = null,
            tint = if (positive) AppGreen else Color(0xFFF7C65C),
            modifier = Modifier.size(16.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = name,
            color = AppTextWhite,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = status,
            color = if (positive) AppGreen else Color(0xFFF7C65C),
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun StrategyPreviewCanvas(
    kind: StrategyKind,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        when (kind) {
            StrategyKind.PATTERN -> {
                drawRoundRect(color = Color.White.copy(alpha = 0.05f), cornerRadius = CornerRadius(18f, 18f))
                drawLine(Color(0xFFE4C64A), Offset(0f, size.height * 0.22f), Offset(size.width, size.height * 0.22f), 4f)
                drawLine(Color(0xFFE4C64A), Offset(0f, size.height * 0.82f), Offset(size.width, size.height * 0.82f), 4f)
                val path = Path().apply {
                    moveTo(size.width * 0.10f, size.height * 0.88f)
                    lineTo(size.width * 0.42f, size.height * 0.18f)
                    lineTo(size.width * 0.70f, size.height * 0.78f)
                    lineTo(size.width * 0.96f, size.height * 0.20f)
                }
                drawPath(path, color = accent, style = Stroke(width = 6f, cap = StrokeCap.Round))
                drawLine(Color.White, Offset(size.width * 0.14f, size.height * 0.82f), Offset(size.width * 0.42f, size.height * 0.18f), 5f)
                drawLine(Color.White, Offset(size.width * 0.42f, size.height * 0.18f), Offset(size.width * 0.74f, size.height * 0.18f), 5f)
                drawLine(Color.White, Offset(size.width * 0.70f, size.height * 0.78f), Offset(size.width * 0.95f, size.height * 0.18f), 5f)
            }

            StrategyKind.TECHNICAL -> {
                repeat(4) { index ->
                    drawLine(
                        color = Color.White.copy(alpha = 0.08f),
                        start = Offset(0f, size.height * (0.18f + index * 0.18f)),
                        end = Offset(size.width, size.height * (0.18f + index * 0.18f)),
                        strokeWidth = 2f
                    )
                }
                for (i in 0 until 7) {
                    val x = size.width * (0.10f + i * 0.11f)
                    val base = size.height * 0.76f
                    val top = size.height * (0.60f - i * 0.04f)
                    drawLine(
                        color = if (i % 2 == 0) accent else Color(0xFFFF5A68),
                        start = Offset(x, base),
                        end = Offset(x, top),
                        strokeWidth = size.width * 0.06f,
                        cap = StrokeCap.Round
                    )
                }
                val guide = Path().apply {
                    moveTo(size.width * 0.10f, size.height * 0.68f)
                    lineTo(size.width * 0.22f, size.height * 0.64f)
                    lineTo(size.width * 0.36f, size.height * 0.58f)
                    lineTo(size.width * 0.54f, size.height * 0.52f)
                    lineTo(size.width * 0.74f, size.height * 0.38f)
                    lineTo(size.width * 0.92f, size.height * 0.28f)
                }
                drawPath(guide, color = Color.White, style = Stroke(width = 4f, cap = StrokeCap.Round))
            }

            StrategyKind.TREND -> {
                repeat(5) { index ->
                    drawLine(
                        color = Color.White.copy(alpha = 0.07f),
                        start = Offset(size.width * (0.10f + index * 0.18f), 0f),
                        end = Offset(size.width * (0.10f + index * 0.18f), size.height),
                        strokeWidth = 2f
                    )
                }
                val trend = Path().apply {
                    moveTo(size.width * 0.06f, size.height * 0.26f)
                    lineTo(size.width * 0.28f, size.height * 0.48f)
                    lineTo(size.width * 0.46f, size.height * 0.38f)
                    lineTo(size.width * 0.64f, size.height * 0.62f)
                    lineTo(size.width * 0.88f, size.height * 0.58f)
                }
                drawPath(trend, color = Color(0xFFFF6675), style = Stroke(width = 6f, cap = StrokeCap.Round))
                drawCircle(Color(0xFFFF8E98), radius = 8f, center = Offset(size.width * 0.18f, size.height * 0.38f))
                drawCircle(Color(0xFFFF8E98), radius = 8f, center = Offset(size.width * 0.50f, size.height * 0.46f))
                drawCircle(Color(0xFFFF8E98), radius = 8f, center = Offset(size.width * 0.74f, size.height * 0.60f))
            }

            StrategyKind.CONDITIONAL -> {
                val blue = Color(0xFF44B9FF)
                val green = Color(0xFFB5ED72)
                drawRoundRect(green, Offset(size.width * 0.06f, size.height * 0.22f), Size(size.width * 0.22f, size.height * 0.20f), CornerRadius(12f, 12f))
                drawRoundRect(blue, Offset(size.width * 0.42f, size.height * 0.32f), Size(size.width * 0.28f, size.height * 0.22f), CornerRadius(12f, 12f))
                drawRoundRect(blue, Offset(size.width * 0.06f, size.height * 0.60f), Size(size.width * 0.22f, size.height * 0.18f), CornerRadius(12f, 12f))
                drawLine(Color.White, Offset(size.width * 0.28f, size.height * 0.32f), Offset(size.width * 0.42f, size.height * 0.43f), 4f)
                drawLine(Color.White, Offset(size.width * 0.28f, size.height * 0.68f), Offset(size.width * 0.42f, size.height * 0.43f), 4f)
                drawLine(Color.White, Offset(size.width * 0.70f, size.height * 0.43f), Offset(size.width * 0.92f, size.height * 0.43f), 4f)
                drawLine(Color.White, Offset(size.width * 0.17f, size.height * 0.08f), Offset(size.width * 0.17f, size.height * 0.22f), 3f)
                drawLine(Color.White, Offset(size.width * 0.17f, size.height * 0.78f), Offset(size.width * 0.17f, size.height * 0.92f), 3f)
            }

            StrategyKind.COMBINATION -> {
                drawRoundRect(color = Color.White.copy(alpha = 0.04f), cornerRadius = CornerRadius(18f, 18f))
            }
        }
    }
}

private data class StrategyFormConfig(
    val title: String,
    val subtitle: String,
    val nameLabel: String,
    val nameValue: String,
    val primaryLabel: String,
    val primaryValue: AnnotatedString,
    val secondaryLabel: String? = null,
    val secondaryValue: AnnotatedString? = null,
    val stockLabel: String = "Select Stock/ Mutual Fund",
    val stockValue: String = "Blue Star + SBC Exports",
    val unitsValue: String = "10",
    val amountValue: String = "",
    val investText: String = "Invest: \u20B9 17840",
    val footerNote: String
)

private fun strategyFormConfig(kind: StrategyKind): StrategyFormConfig = when (kind) {
    StrategyKind.TECHNICAL -> StrategyFormConfig(
        title = "Technical Analysis",
        subtitle = "Use indicator-based logic to detect momentum, mean reversion, and trade timing with discipline.",
        nameLabel = "Intelligence Strategy Name",
        nameValue = "Technical Test 1",
        primaryLabel = "Select Technical Aspect",
        primaryValue = AnnotatedString("RSI"),
        secondaryLabel = "Select Condition",
        secondaryValue = AnnotatedString("Below 35"),
        footerNote = "RSI below 35 can surface oversold zones. Pairing it with a stock basket helps you stage alert-first entries before confirming an order."
    )

    StrategyKind.PATTERN -> StrategyFormConfig(
        title = "Pattern Analysis",
        subtitle = "Translate recognizable chart structures into repeatable trade rules with clear breakout intent.",
        nameLabel = "Intelligence Strategy Name",
        nameValue = "Pattern Test 1",
        primaryLabel = "Select Pattern",
        primaryValue = AnnotatedString("Ascending Triangle"),
        secondaryLabel = "Select Breakout Type",
        secondaryValue = AnnotatedString("Aggressive Breakout"),
        footerNote = "Ascending triangle setups reward patience. Aggressive breakout execution works best when volume expansion confirms the move."
    )

    StrategyKind.CONDITIONAL -> StrategyFormConfig(
        title = "Conditional Analysis",
        subtitle = "Define event-driven market logic that reacts to thresholds, anomalies, and portfolio-specific rules.",
        nameLabel = "Intelligence Condition Name",
        nameValue = "Condition Test 1",
        primaryLabel = "Select Condition",
        primaryValue = buildAnnotatedString {
            append("Price change by ")
            pushStyle(SpanStyle(color = Color(0xFFFF5A68), fontWeight = FontWeight.Bold))
            append("-4%")
            pop()
        },
        footerNote = "A price move threshold is useful for quick reaction automation. Start with alerts, then graduate to orders once slippage tolerance is validated."
    )

    StrategyKind.TREND -> StrategyFormConfig(
        title = "Trend Analysis",
        subtitle = "Follow directional conviction and let momentum structure decide where action is justified.",
        nameLabel = "Intelligence Strategy Name",
        nameValue = "Trend Test 1",
        primaryLabel = "Select Trend",
        primaryValue = AnnotatedString("Down Trend"),
        secondaryLabel = "Select Breakout Type",
        secondaryValue = AnnotatedString("Aggressive Breakout"),
        footerNote = "Trend-led strategies perform best when the broader direction is clear. This setup can help you wait for stronger continuation entries."
    )

    StrategyKind.COMBINATION -> StrategyFormConfig(
        title = "Combinations",
        subtitle = "Blend multiple signal families into one advanced strategy pipeline.",
        nameLabel = "Strategy Name",
        nameValue = "Combination Test 1",
        primaryLabel = "Primary Logic",
        primaryValue = AnnotatedString("Pattern + RSI"),
        secondaryLabel = "Execution Mode",
        secondaryValue = AnnotatedString("Alert first"),
        footerNote = "Combination strategies are reserved for advanced builder releases. Locking them now keeps the current experience focused and reliable."
    )
}

private fun strategyAccent(kind: StrategyKind): Color = when (kind) {
    StrategyKind.PATTERN -> Color(0xFFFFC857)
    StrategyKind.TECHNICAL -> Color(0xFF63E6BE)
    StrategyKind.CONDITIONAL -> Color(0xFF6CCBFF)
    StrategyKind.TREND -> Color(0xFFFF7A8A)
    StrategyKind.COMBINATION -> Color(0xFFB0B7C3)
}
