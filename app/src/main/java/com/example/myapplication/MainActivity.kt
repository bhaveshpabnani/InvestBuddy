package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.components.AppBottomBar
import com.example.myapplication.ui.components.AppTab
import com.example.myapplication.ui.components.AppTopBar
import com.example.myapplication.ui.screens.*
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

// ── All possible screens in the app ──────────────────────────────────────────
enum class Screen {
    // Auth flow
    Welcome, Login, Register, ForgotPassword, CreateNewPassword, VerifyAccount,
    // Main app screens (with bottom nav)
    Home, Profile, OrdersAndTransactions, EditProfile,
    // Main tab extensions
    Analytics, Strategies, AskBuddy, BuddyBuilder,
    PatternAnalysis, TechnicalAnalysis, ConditionalAnalysis, TrendAnalysis, StrategyFiling
}

// ── Root navigation ────────────────────────────────────────────────────────────
@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf(Screen.Welcome) }

    when {
        // ── Auth screens ─────────────────────────────────────────────────────
        currentScreen == Screen.Welcome -> WelcomeScreen(
            onCreateAccountClick = { currentScreen = Screen.Register },
            onLoginClick = { currentScreen = Screen.Login }
        )
        currentScreen == Screen.Login -> LoginScreen(
            onBackClick = { currentScreen = Screen.Welcome },
            onLoginClick = { currentScreen = Screen.Home },
            onForgotPasswordClick = { currentScreen = Screen.ForgotPassword },
            onGoogleLoginClick = { currentScreen = Screen.Home }
        )
        currentScreen == Screen.Register -> RegisterScreen(
            onBackClick = { currentScreen = Screen.Welcome },
            onCreateAccountClick = { currentScreen = Screen.VerifyAccount },
            onTermsClick = {},
            onPrivacyClick = {}
        )
        currentScreen == Screen.ForgotPassword -> ForgotPasswordScreen(
            onBackClick = { currentScreen = Screen.Login },
            onSendInstructionClick = { currentScreen = Screen.CreateNewPassword }
        )
        currentScreen == Screen.CreateNewPassword -> CreateNewPasswordScreen(
            onBackClick = { currentScreen = Screen.ForgotPassword },
            onResetPasswordClick = { currentScreen = Screen.Login }
        )
        currentScreen == Screen.VerifyAccount -> VerifyAccountScreen(
            onBackClick = { currentScreen = Screen.Register },
            onVerifyClick = { currentScreen = Screen.Home },
            onResendClick = {}
        )

        // ── Sub-screens (no bottom nav) ───────────────────────────────────────
        currentScreen == Screen.OrdersAndTransactions -> OrdersAndTransactionsScreen(
            onBackClick = { currentScreen = Screen.Profile }
        )
        currentScreen == Screen.EditProfile -> EditNotActiveScreen()

        // ── Main app screens (with shared bottom nav + top bar) ───────────────
        else -> MainAppScaffold(
            currentScreen = currentScreen,
            onScreenChange = { currentScreen = it }
        )
    }
}

// ── Scaffold that wraps the main tabs with TopBar + BottomBar ─────────────────
@Composable
fun MainAppScaffold(
    currentScreen: Screen,
    onScreenChange: (Screen) -> Unit
) {
    val currentTab = when (currentScreen) {
        Screen.Home       -> AppTab.HOME
        Screen.Analytics  -> AppTab.ANALYTICS
        Screen.AskBuddy   -> AppTab.BUDDY
        Screen.BuddyBuilder -> AppTab.STRATEGIES
        Screen.PatternAnalysis -> AppTab.STRATEGIES
        Screen.TechnicalAnalysis -> AppTab.STRATEGIES
        Screen.ConditionalAnalysis -> AppTab.STRATEGIES
        Screen.TrendAnalysis -> AppTab.STRATEGIES
        Screen.Strategies -> AppTab.STRATEGIES
        Screen.Profile    -> AppTab.PROFILE
        else              -> AppTab.HOME
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // ── Top bar shared across all main screens ────────────────────────
        AppTopBar(
            name = "Preet Panchal",
            balance = "₹18556",
            onSearchClick = {},
            onNotificationClick = {}
        )

        // ── Screen content ────────────────────────────────────────────────
        Column(modifier = Modifier.weight(1f)) {
            when (currentScreen) {
                Screen.Home -> HomeScreen(
                    onDepositClick = {},
                    onWithdrawClick = {},
                    onSeeAllViewedClick = { onScreenChange(Screen.AskBuddy) },
                    onSeeAllWatchlistClick = { onScreenChange(Screen.AskBuddy) }
                )
                Screen.Profile -> ProfileScreen(
                    onDepositClick = {},
                    onWithdrawClick = {},
                    onViewHistoryClick = { onScreenChange(Screen.OrdersAndTransactions) },
                    onEditClick = { onScreenChange(Screen.EditProfile) },
                    onSaveClick = {}
                )
                Screen.Analytics -> AnalyticsScreen(
                    onDepositClick = {},
                    onWithdrawClick = {}
                )
                Screen.AskBuddy -> AskBuddyInvestmentScreen()
                Screen.BuddyBuilder -> AskBuddyHubScreen(
                    onStrategySelected = { kind ->
                        onScreenChange(
                            when (kind) {
                                com.example.myapplication.ui.model.StrategyKind.PATTERN -> Screen.PatternAnalysis
                                com.example.myapplication.ui.model.StrategyKind.TECHNICAL -> Screen.TechnicalAnalysis
                                com.example.myapplication.ui.model.StrategyKind.CONDITIONAL -> Screen.ConditionalAnalysis
                                com.example.myapplication.ui.model.StrategyKind.TREND -> Screen.TrendAnalysis
                                com.example.myapplication.ui.model.StrategyKind.COMBINATION -> Screen.BuddyBuilder
                            }
                        )
                    },
                    onSavedClick = { onScreenChange(Screen.Strategies) }
                )
                Screen.Strategies -> SavedStrategiesScreen(
                    onCreateNewClick = { onScreenChange(Screen.BuddyBuilder) },
                    onFileStrategyClick = { onScreenChange(Screen.StrategyFiling) },
                    onStrategySelected = { kind ->
                        onScreenChange(
                            when (kind) {
                                com.example.myapplication.ui.model.StrategyKind.PATTERN -> Screen.PatternAnalysis
                                com.example.myapplication.ui.model.StrategyKind.TECHNICAL -> Screen.TechnicalAnalysis
                                com.example.myapplication.ui.model.StrategyKind.CONDITIONAL -> Screen.ConditionalAnalysis
                                com.example.myapplication.ui.model.StrategyKind.TREND -> Screen.TrendAnalysis
                                com.example.myapplication.ui.model.StrategyKind.COMBINATION -> Screen.Strategies
                            }
                        )
                    }
                )
                Screen.PatternAnalysis -> StrategyDetailScreen(
                    kind = com.example.myapplication.ui.model.StrategyKind.PATTERN,
                    onBackClick = { onScreenChange(Screen.BuddyBuilder) },
                    onSavedClick = { onScreenChange(Screen.Strategies) },
                    onFileStrategyClick = { onScreenChange(Screen.StrategyFiling) }
                )
                Screen.TechnicalAnalysis -> StrategyDetailScreen(
                    kind = com.example.myapplication.ui.model.StrategyKind.TECHNICAL,
                    onBackClick = { onScreenChange(Screen.BuddyBuilder) },
                    onSavedClick = { onScreenChange(Screen.Strategies) },
                    onFileStrategyClick = { onScreenChange(Screen.StrategyFiling) }
                )
                Screen.ConditionalAnalysis -> StrategyDetailScreen(
                    kind = com.example.myapplication.ui.model.StrategyKind.CONDITIONAL,
                    onBackClick = { onScreenChange(Screen.BuddyBuilder) },
                    onSavedClick = { onScreenChange(Screen.Strategies) },
                    onFileStrategyClick = { onScreenChange(Screen.StrategyFiling) }
                )
                Screen.TrendAnalysis -> StrategyDetailScreen(
                    kind = com.example.myapplication.ui.model.StrategyKind.TREND,
                    onBackClick = { onScreenChange(Screen.BuddyBuilder) },
                    onSavedClick = { onScreenChange(Screen.Strategies) },
                    onFileStrategyClick = { onScreenChange(Screen.StrategyFiling) }
                )
                Screen.StrategyFiling -> StrategyFilingScreen(
                    onBackClick = { onScreenChange(Screen.Strategies) },
                    onSubmitClick = { onScreenChange(Screen.Strategies) }
                )
                else -> {}
            }
        }

        // ── Bottom navigation ─────────────────────────────────────────────
        AppBottomBar(
            currentTab = currentTab,
            onTabChange = { tab ->
                onScreenChange(
                    when (tab) {
                        AppTab.HOME       -> Screen.Home
                        AppTab.ANALYTICS  -> Screen.Analytics
                        AppTab.BUDDY      -> Screen.AskBuddy
                        AppTab.STRATEGIES -> Screen.Strategies
                        AppTab.PROFILE    -> Screen.Profile
                    }
                )
            }
        )
    }
}

// ── Simple placeholder for unimplemented tabs ─────────────────────────────────
@Composable
fun PlaceholderScreen(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(com.example.myapplication.ui.components.AppBackground),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = com.example.myapplication.ui.components.AppTextGrey,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}
