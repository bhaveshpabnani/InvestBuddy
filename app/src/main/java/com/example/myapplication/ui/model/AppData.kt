package com.example.myapplication.ui.model

// ── Data models ───────────────────────────────────────────────────────────────

data class StockItem(
    val ticker: String,
    val company: String,
    val price: String,
    val change: String,
    val isPositive: Boolean,
    val chartPoints: List<Float>
)

enum class PortfolioSliceType {
    STOCKS, MFS, BONDS
}

data class PortfolioSlice(
    val type: PortfolioSliceType,
    val label: String,
    val percentage: Int,
    val amount: String,
    val returns: String
)

enum class TransactionType {
    BUY, SELL, WITHDRAW, DEPOSIT
}

enum class TransactionCategory {
    STOCKS, MFS, BONDS, STRATEGIES, TRANSFERS
}

data class Transaction(
    val date: String,
    val type: TransactionType,
    val name: String,
    val time: String,
    val deliveryType: String,   // "Delivery" | "Strategy" | "Transfer"
    val quantityOrAmount: String,
    val avgPrice: String,       // empty for transfers
    val category: TransactionCategory
)

enum class StrategyKind {
    PATTERN, TECHNICAL, CONDITIONAL, TREND, COMBINATION
}

data class StrategyIdeaCard(
    val kind: StrategyKind,
    val title: String,
    val subtitle: String,
    val supportingText: String,
    val isLocked: Boolean = false,
    val previewPoints: List<Float> = emptyList()
)

data class StrategyDraftSummary(
    val kind: StrategyKind,
    val title: String,
    val instrument: String,
    val trigger: String,
    val status: String,
    val capital: String
)

// ── Sample data ───────────────────────────────────────────────────────────────
object SampleData {

    val portfolioValue = "₹162,120"
    val portfolioReturns = "+62.12%"

    val portfolioSlices = listOf(
        PortfolioSlice(
            type = PortfolioSliceType.STOCKS,
            label = "Stocks",
            percentage = 32,
            amount = "₹50000",
            returns = "+62.12%"
        ),
        PortfolioSlice(
            type = PortfolioSliceType.MFS,
            label = "MFs",
            percentage = 38,
            amount = "₹62120",
            returns = "+62.12%"
        ),
        PortfolioSlice(
            type = PortfolioSliceType.BONDS,
            label = "Bonds",
            percentage = 32,
            amount = "₹50000",
            returns = "+62.12%"
        )
    )

    val viewedStocks = listOf(
        StockItem(
            "ABFRL", "Aditya Bir...", "₹ 58.31", "+0.56%", true,
            listOf(42f, 48f, 44f, 58f, 52f, 62f, 58f)
        ),
        StockItem(
            "SBC", "SBC Exp...", "₹ 31.33", "-1.23%", false,
            listOf(48f, 45f, 52f, 42f, 46f, 36f, 31f)
        )
    )

    val watchlistStocks = listOf(
        StockItem(
            "ABFRL", "Aditya Bir...", "₹ 58.31", "+0.56%", true,
            listOf(42f, 48f, 44f, 58f, 52f, 62f, 58f)
        )
    )

    val allTransactions = listOf(
        // Bonds
        Transaction("28 Mar 2026", TransactionType.BUY, "SBC Bonds",
            "09:52 AM", "Delivery", "20", "Avg ₹100", TransactionCategory.BONDS),

        // Transfers
        Transaction("27 Mar 2026", TransactionType.WITHDRAW, "UPI",
            "09:52 AM", "Transfer", "₹10000", "", TransactionCategory.TRANSFERS),

        // MFs
        Transaction("26 Mar 2026", TransactionType.SELL, "Axis Bank Mutual Fund",
            "09:52 AM", "Delivery", "123.33", "Avg ₹4009.23", TransactionCategory.MFS),
        Transaction("25 Mar 2026", TransactionType.BUY, "Axis Bank Mutual Fund",
            "09:52 AM", "Delivery", "123.33", "Avg ₹4000", TransactionCategory.MFS),

        // Stocks (mixed delivery + strategy)
        Transaction("21 Mar 2026", TransactionType.SELL, "Varun Beverages",
            "09:52 AM", "Delivery", "4", "Avg ₹490", TransactionCategory.STOCKS),
        Transaction("20 Mar 2026", TransactionType.BUY, "Varun Beverages",
            "09:11 AM", "Strategy", "2", "Avg ₹470", TransactionCategory.STOCKS),
        Transaction("16 Mar 2026", TransactionType.BUY, "Varun Beverages",
            "09:52 AM", "Delivery", "2", "Avg ₹470", TransactionCategory.STOCKS),

        // Transfers
        Transaction("13 Mar 2026", TransactionType.DEPOSIT, "UPI",
            "09:03 AM", "Transfer", "₹20000", "", TransactionCategory.TRANSFERS),

        // Stocks (continued)
        Transaction("12 Feb 2026", TransactionType.SELL, "Varun Beverages",
            "09:52 AM", "Delivery", "4", "Avg ₹490", TransactionCategory.STOCKS),
        Transaction("11 Feb 2026", TransactionType.BUY, "Varun Beverages",
            "09:11 AM", "Strategy", "2", "Avg ₹470", TransactionCategory.STOCKS),

        // Transfers (older)
        Transaction("21 Feb 2026", TransactionType.WITHDRAW, "UPI",
            "09:52 AM", "Transfer", "₹10000", "", TransactionCategory.TRANSFERS),

        // MFs (older)
        Transaction("20 Feb 2026", TransactionType.SELL, "Axis Bank Mutual Fund",
            "09:52 AM", "Delivery", "123.33", "Avg ₹4009.23", TransactionCategory.MFS),
        Transaction("14 Feb 2026", TransactionType.BUY, "Axis Bank Mutual Fund",
            "09:52 AM", "Delivery", "123.33", "Avg ₹4000", TransactionCategory.MFS),

        // Transfers (oldest)
        Transaction("1 Feb 2026", TransactionType.DEPOSIT, "UPI",
            "09:03 AM", "Transfer", "₹20000", "", TransactionCategory.TRANSFERS)
    )

    /** Filter helper: Strategies = any transaction placed via Strategy order type */
    fun transactionsFor(cat: String): List<Transaction> = when (cat) {
        "Stocks"     -> allTransactions.filter { it.category == TransactionCategory.STOCKS }
        "MFs"        -> allTransactions.filter { it.category == TransactionCategory.MFS }
        "Bonds"      -> allTransactions.filter { it.category == TransactionCategory.BONDS }
        "Strategies" -> allTransactions.filter { it.deliveryType == "Strategy" }
        "Transfers"  -> allTransactions.filter { it.category == TransactionCategory.TRANSFERS }
        else         -> allTransactions   // "All"
    }

    val strategyIdeas = listOf(
        StrategyIdeaCard(
            kind = StrategyKind.PATTERN,
            title = "Pattern Analysis",
            subtitle = "Visual chart structures",
            supportingText = "Build setups around breakout and continuation patterns.",
            previewPoints = listOf(34f, 66f, 28f, 74f, 46f, 82f, 58f)
        ),
        StrategyIdeaCard(
            kind = StrategyKind.TECHNICAL,
            title = "Technical Analysis",
            subtitle = "Indicators and momentum",
            supportingText = "Create rules using RSI, MACD, volume, and moving averages.",
            previewPoints = listOf(22f, 24f, 30f, 40f, 44f, 68f, 76f)
        ),
        StrategyIdeaCard(
            kind = StrategyKind.TREND,
            title = "Trend Analysis",
            subtitle = "Follow directional bias",
            supportingText = "Trade with persistent strength or weakness across sessions.",
            previewPoints = listOf(72f, 58f, 44f, 38f, 32f, 29f, 33f)
        ),
        StrategyIdeaCard(
            kind = StrategyKind.CONDITIONAL,
            title = "Conditional Analysis",
            subtitle = "Event-based logic",
            supportingText = "Combine triggers, thresholds, and instrument filters.",
            previewPoints = listOf(30f, 30f, 52f, 52f, 36f, 68f, 68f)
        ),
        StrategyIdeaCard(
            kind = StrategyKind.COMBINATION,
            title = "Combinations",
            subtitle = "Multi-factor intelligence",
            supportingText = "Blend multiple signals into one deployable setup.",
            isLocked = true
        )
    )

    val savedStrategies = listOf(
        StrategyDraftSummary(
            kind = StrategyKind.TECHNICAL,
            title = "Technical Test 1",
            instrument = "Blue Star + SBC Exports",
            trigger = "RSI below 35",
            status = "Alert Active",
            capital = "\u20B9 17,840"
        ),
        StrategyDraftSummary(
            kind = StrategyKind.PATTERN,
            title = "Pattern Test 1",
            instrument = "Blue Star + SBC Exports",
            trigger = "Ascending Triangle breakout",
            status = "Order Ready",
            capital = "\u20B9 17,840"
        ),
        StrategyDraftSummary(
            kind = StrategyKind.TREND,
            title = "Trend Test 1",
            instrument = "Blue Star + SBC Exports",
            trigger = "Down trend + aggressive breakout",
            status = "Monitoring",
            capital = "\u20B9 17,840"
        )
    )
}
