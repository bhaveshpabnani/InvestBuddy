package com.example.myapplication.ui.model

import androidx.compose.runtime.mutableStateListOf

enum class BuddyAssetClass {
    STOCKS,
    MFS,
    BONDS
}

enum class BuddyTrend {
    POSITIVE,
    NEGATIVE,
    NEUTRAL
}

data class BuddyMarketItem(
    val id: String,
    val category: BuddyAssetClass,
    val name: String,
    val primaryValue: String,
    val secondaryValue: String? = null,
    val trend: BuddyTrend = BuddyTrend.NEUTRAL
)

data class BuddyRating(
    val label: String,
    val score: Int
)

data class BuddyRangePoints(
    val label: String,
    val points: List<Float>
)

sealed interface BuddyDetailInfo {
    val id: String
    val name: String
    val ratings: List<BuddyRating>
    val aiSuggestion: String
    val aiSuggestionTrend: BuddyTrend
    val primaryAction: String
    val secondaryAction: String
}

data class BuddyEquityDetail(
    override val id: String,
    override val name: String,
    val headlineValue: String,
    val headlineChange: String,
    val headlineHorizon: String,
    val ranges: List<BuddyRangePoints>,
    val holdingTitle: String,
    val holdingCaption: String,
    val holdingValue: String,
    val holdingValueChange: String,
    val holdingValueTrend: BuddyTrend,
    override val ratings: List<BuddyRating>,
    override val aiSuggestion: String,
    override val aiSuggestionTrend: BuddyTrend,
    override val primaryAction: String,
    override val secondaryAction: String
) : BuddyDetailInfo

data class BuddyBondDetail(
    override val id: String,
    override val name: String,
    val headlineValue: String,
    val unitsValue: String,
    val avgPrice: String,
    val holdingValue: String,
    override val ratings: List<BuddyRating>,
    override val aiSuggestion: String,
    override val aiSuggestionTrend: BuddyTrend,
    override val primaryAction: String,
    override val secondaryAction: String
) : BuddyDetailInfo

object BuddyMarketData {

    private val watchlistIds = mutableStateListOf(
        "godfrey-philips"
    )

    val marketItems = listOf(
        BuddyMarketItem(
            id = "godfrey-philips",
            category = BuddyAssetClass.STOCKS,
            name = "Godfrey Philips",
            primaryValue = "\u20B91901",
            secondaryValue = "-7.46%",
            trend = BuddyTrend.NEGATIVE
        ),
        BuddyMarketItem(
            id = "lic-housing-finance",
            category = BuddyAssetClass.STOCKS,
            name = "LIC Housing Finance",
            primaryValue = "\u20B9501.1",
            secondaryValue = "+1.96%",
            trend = BuddyTrend.POSITIVE
        ),
        BuddyMarketItem(
            id = "oil-india",
            category = BuddyAssetClass.STOCKS,
            name = "Oil India",
            primaryValue = "\u20B9478.1",
            secondaryValue = "+1.40%",
            trend = BuddyTrend.POSITIVE
        ),
        BuddyMarketItem(
            id = "paytm",
            category = BuddyAssetClass.STOCKS,
            name = "Paytm",
            primaryValue = "\u20B91008",
            secondaryValue = "-5.36%",
            trend = BuddyTrend.NEGATIVE
        ),
        BuddyMarketItem(
            id = "sbi-psu-mf",
            category = BuddyAssetClass.MFS,
            name = "SBI PSU",
            primaryValue = "\u20B9499",
            secondaryValue = "+1.26%",
            trend = BuddyTrend.POSITIVE
        ),
        BuddyMarketItem(
            id = "axis-gold-mf",
            category = BuddyAssetClass.MFS,
            name = "Axis Gold",
            primaryValue = "\u20B9601.3",
            secondaryValue = "+2.96%",
            trend = BuddyTrend.POSITIVE
        ),
        BuddyMarketItem(
            id = "nippon-india-mf",
            category = BuddyAssetClass.MFS,
            name = "Nippon India",
            primaryValue = "\u20B9274.1",
            secondaryValue = "-2.06%",
            trend = BuddyTrend.NEGATIVE
        ),
        BuddyMarketItem(
            id = "uti-nifty-mf",
            category = BuddyAssetClass.MFS,
            name = "UTI Nifty",
            primaryValue = "\u20B9287.7",
            secondaryValue = "-2.21%",
            trend = BuddyTrend.NEGATIVE
        ),
        BuddyMarketItem(
            id = "sbi-bond",
            category = BuddyAssetClass.BONDS,
            name = "SBI Bond",
            primaryValue = "10%"
        ),
        BuddyMarketItem(
            id = "rbi-bond",
            category = BuddyAssetClass.BONDS,
            name = "RBI Bond",
            primaryValue = "11.2%"
        ),
        BuddyMarketItem(
            id = "sun-pharma-bond",
            category = BuddyAssetClass.BONDS,
            name = "Sun Pharma Bond",
            primaryValue = "12%"
        ),
        BuddyMarketItem(
            id = "gold-bond",
            category = BuddyAssetClass.BONDS,
            name = "Gold Bond",
            primaryValue = "9%"
        )
    )

    private val detailLookup = mapOf<String, BuddyDetailInfo>(
        "godfrey-philips" to BuddyEquityDetail(
            id = "godfrey-philips",
            name = "Godfrey Philips",
            headlineValue = "\u20B91901",
            headlineChange = "-7.46%",
            headlineHorizon = "1D",
            ranges = listOf(
                BuddyRangePoints(
                    label = "1D",
                    points = listOf(
                        74f, 78f, 72f, 75f, 71f, 73f, 70f, 69f, 66f, 67f,
                        64f, 63f, 62f, 63f, 61f, 58f, 57f, 56f, 58f, 55f,
                        54f, 53f, 52f, 53f, 52f, 51f, 52f, 53f, 57f, 56f,
                        54f, 55f, 56f, 54f, 48f, 44f, 45f, 43f, 44f
                    )
                ),
                BuddyRangePoints(
                    label = "1W",
                    points = listOf(86f, 84f, 82f, 80f, 78f, 79f, 76f, 74f, 72f, 70f, 68f, 69f)
                ),
                BuddyRangePoints(
                    label = "1M",
                    points = listOf(92f, 88f, 84f, 81f, 78f, 74f, 72f, 69f, 66f, 64f, 60f, 58f)
                ),
                BuddyRangePoints(
                    label = "3M",
                    points = listOf(98f, 93f, 91f, 86f, 83f, 78f, 74f, 72f, 69f, 66f, 63f, 61f)
                ),
                BuddyRangePoints(
                    label = "1Y",
                    points = listOf(95f, 90f, 82f, 86f, 80f, 74f, 70f, 66f, 69f, 62f, 58f, 52f)
                ),
                BuddyRangePoints(
                    label = "All",
                    points = listOf(62f, 65f, 68f, 66f, 64f, 70f, 75f, 72f, 67f, 61f, 54f, 44f)
                )
            ),
            holdingTitle = "7 Shares",
            holdingCaption = "Avg Price \u20B91900",
            holdingValue = "\u20B913307",
            holdingValueChange = "+0.05%",
            holdingValueTrend = BuddyTrend.POSITIVE,
            ratings = listOf(
                BuddyRating("Overall", 7),
                BuddyRating("Technical", 8),
                BuddyRating("Financial", 9),
                BuddyRating("Industry", 5),
                BuddyRating("Advisors", 7)
            ),
            aiSuggestion = "Buddy.ai suggests to SELL position analyzing your profile",
            aiSuggestionTrend = BuddyTrend.NEGATIVE,
            primaryAction = "SELL",
            secondaryAction = "BUY"
        ),
        "lic-housing-finance" to BuddyEquityDetail(
            id = "lic-housing-finance",
            name = "LIC Housing Finance",
            headlineValue = "\u20B9501.1",
            headlineChange = "+1.96%",
            headlineHorizon = "1D",
            ranges = listOf(
                BuddyRangePoints("1D", listOf(42f, 43f, 44f, 45f, 46f, 45f, 47f, 48f, 49f, 50f, 51f, 52f, 53f, 54f)),
                BuddyRangePoints("1W", listOf(36f, 38f, 40f, 42f, 44f, 46f, 48f, 50f, 52f)),
                BuddyRangePoints("1M", listOf(30f, 32f, 36f, 40f, 42f, 44f, 48f, 51f, 55f)),
                BuddyRangePoints("3M", listOf(28f, 29f, 33f, 38f, 41f, 46f, 50f, 54f, 58f)),
                BuddyRangePoints("1Y", listOf(24f, 28f, 26f, 33f, 39f, 35f, 42f, 49f, 54f)),
                BuddyRangePoints("All", listOf(20f, 24f, 22f, 30f, 36f, 41f, 46f, 50f, 54f))
            ),
            holdingTitle = "20 Shares",
            holdingCaption = "Avg Price \u20B9480",
            holdingValue = "\u20B910022",
            holdingValueChange = "+4.40%",
            holdingValueTrend = BuddyTrend.POSITIVE,
            ratings = listOf(
                BuddyRating("Overall", 8),
                BuddyRating("Technical", 7),
                BuddyRating("Financial", 8),
                BuddyRating("Industry", 7),
                BuddyRating("Advisors", 6)
            ),
            aiSuggestion = "Buddy.ai suggests to BUY on dips based on your profile",
            aiSuggestionTrend = BuddyTrend.POSITIVE,
            primaryAction = "SELL",
            secondaryAction = "BUY"
        ),
        "oil-india" to BuddyEquityDetail(
            id = "oil-india",
            name = "Oil India",
            headlineValue = "\u20B9478.1",
            headlineChange = "+1.40%",
            headlineHorizon = "1D",
            ranges = listOf(
                BuddyRangePoints("1D", listOf(52f, 51f, 52f, 53f, 54f, 53f, 55f, 56f, 55f, 57f, 58f, 59f, 60f)),
                BuddyRangePoints("1W", listOf(44f, 45f, 46f, 48f, 49f, 50f, 52f, 54f, 56f)),
                BuddyRangePoints("1M", listOf(36f, 38f, 40f, 43f, 46f, 48f, 50f, 54f, 58f)),
                BuddyRangePoints("3M", listOf(34f, 37f, 39f, 43f, 47f, 50f, 54f, 58f, 62f)),
                BuddyRangePoints("1Y", listOf(30f, 34f, 33f, 38f, 44f, 47f, 45f, 51f, 57f)),
                BuddyRangePoints("All", listOf(28f, 30f, 35f, 39f, 43f, 47f, 52f, 57f, 60f))
            ),
            holdingTitle = "15 Shares",
            holdingCaption = "Avg Price \u20B9460",
            holdingValue = "\u20B97171",
            holdingValueChange = "+3.90%",
            holdingValueTrend = BuddyTrend.POSITIVE,
            ratings = listOf(
                BuddyRating("Overall", 8),
                BuddyRating("Technical", 6),
                BuddyRating("Financial", 8),
                BuddyRating("Industry", 8),
                BuddyRating("Advisors", 7)
            ),
            aiSuggestion = "Buddy.ai suggests to HOLD while trend strength remains stable",
            aiSuggestionTrend = BuddyTrend.NEUTRAL,
            primaryAction = "SELL",
            secondaryAction = "BUY"
        ),
        "paytm" to BuddyEquityDetail(
            id = "paytm",
            name = "Paytm",
            headlineValue = "\u20B91008",
            headlineChange = "-5.36%",
            headlineHorizon = "1D",
            ranges = listOf(
                BuddyRangePoints("1D", listOf(80f, 78f, 77f, 74f, 75f, 71f, 69f, 66f, 64f, 62f, 60f, 58f, 56f)),
                BuddyRangePoints("1W", listOf(84f, 82f, 78f, 76f, 73f, 70f, 68f, 64f, 60f)),
                BuddyRangePoints("1M", listOf(92f, 88f, 84f, 80f, 76f, 72f, 68f, 63f, 58f)),
                BuddyRangePoints("3M", listOf(96f, 92f, 88f, 84f, 78f, 74f, 70f, 66f, 60f)),
                BuddyRangePoints("1Y", listOf(99f, 94f, 89f, 82f, 86f, 74f, 70f, 64f, 58f)),
                BuddyRangePoints("All", listOf(90f, 86f, 82f, 78f, 73f, 68f, 64f, 60f, 56f))
            ),
            holdingTitle = "10 Shares",
            holdingCaption = "Avg Price \u20B91060",
            holdingValue = "\u20B910080",
            holdingValueChange = "-4.90%",
            holdingValueTrend = BuddyTrend.NEGATIVE,
            ratings = listOf(
                BuddyRating("Overall", 6),
                BuddyRating("Technical", 5),
                BuddyRating("Financial", 6),
                BuddyRating("Industry", 6),
                BuddyRating("Advisors", 5)
            ),
            aiSuggestion = "Buddy.ai suggests to reduce risk exposure in your current profile",
            aiSuggestionTrend = BuddyTrend.NEGATIVE,
            primaryAction = "SELL",
            secondaryAction = "BUY"
        ),
        "sbi-psu-mf" to BuddyEquityDetail(
            id = "sbi-psu-mf",
            name = "SBI PSU",
            headlineValue = "\u20B9499",
            headlineChange = "+1.26%",
            headlineHorizon = "1D",
            ranges = listOf(
                BuddyRangePoints("1D", listOf(48f, 49f, 50f, 51f, 50f, 52f, 53f, 54f, 53f, 55f, 56f, 57f)),
                BuddyRangePoints("1W", listOf(42f, 43f, 45f, 46f, 47f, 49f, 51f, 53f, 55f)),
                BuddyRangePoints("1M", listOf(34f, 36f, 39f, 42f, 46f, 49f, 53f, 56f, 59f)),
                BuddyRangePoints("3M", listOf(30f, 33f, 36f, 41f, 45f, 49f, 54f, 58f, 62f)),
                BuddyRangePoints("1Y", listOf(28f, 31f, 30f, 35f, 40f, 44f, 48f, 53f, 58f)),
                BuddyRangePoints("All", listOf(24f, 28f, 32f, 37f, 42f, 47f, 52f, 56f, 60f))
            ),
            holdingTitle = "42 Units",
            holdingCaption = "Avg NAV \u20B9488",
            holdingValue = "\u20B920958",
            holdingValueChange = "+2.25%",
            holdingValueTrend = BuddyTrend.POSITIVE,
            ratings = listOf(
                BuddyRating("Overall", 8),
                BuddyRating("Technical", 7),
                BuddyRating("Financial", 8),
                BuddyRating("Industry", 7),
                BuddyRating("Advisors", 7)
            ),
            aiSuggestion = "Buddy.ai suggests to HOLD with periodic SIP additions",
            aiSuggestionTrend = BuddyTrend.NEUTRAL,
            primaryAction = "SELL",
            secondaryAction = "BUY"
        ),
        "axis-gold-mf" to BuddyEquityDetail(
            id = "axis-gold-mf",
            name = "Axis Gold",
            headlineValue = "\u20B9601.3",
            headlineChange = "+2.96%",
            headlineHorizon = "1D",
            ranges = listOf(
                BuddyRangePoints("1D", listOf(46f, 47f, 49f, 51f, 52f, 54f, 56f, 58f, 60f, 61f, 63f, 65f)),
                BuddyRangePoints("1W", listOf(40f, 42f, 44f, 46f, 48f, 50f, 53f, 56f, 59f)),
                BuddyRangePoints("1M", listOf(33f, 36f, 40f, 44f, 48f, 52f, 56f, 60f, 64f)),
                BuddyRangePoints("3M", listOf(29f, 33f, 37f, 42f, 47f, 52f, 57f, 62f, 67f)),
                BuddyRangePoints("1Y", listOf(26f, 30f, 34f, 39f, 43f, 48f, 53f, 58f, 63f)),
                BuddyRangePoints("All", listOf(22f, 27f, 32f, 37f, 43f, 49f, 55f, 60f, 66f))
            ),
            holdingTitle = "36 Units",
            holdingCaption = "Avg NAV \u20B9580",
            holdingValue = "\u20B921646",
            holdingValueChange = "+3.67%",
            holdingValueTrend = BuddyTrend.POSITIVE,
            ratings = listOf(
                BuddyRating("Overall", 8),
                BuddyRating("Technical", 8),
                BuddyRating("Financial", 8),
                BuddyRating("Industry", 8),
                BuddyRating("Advisors", 7)
            ),
            aiSuggestion = "Buddy.ai suggests to continue staggered accumulation",
            aiSuggestionTrend = BuddyTrend.POSITIVE,
            primaryAction = "SELL",
            secondaryAction = "BUY"
        ),
        "nippon-india-mf" to BuddyEquityDetail(
            id = "nippon-india-mf",
            name = "Nippon India",
            headlineValue = "\u20B9274.1",
            headlineChange = "-2.06%",
            headlineHorizon = "1D",
            ranges = listOf(
                BuddyRangePoints("1D", listOf(60f, 59f, 58f, 57f, 56f, 55f, 54f, 53f, 52f, 51f, 50f, 49f)),
                BuddyRangePoints("1W", listOf(64f, 63f, 61f, 59f, 57f, 55f, 53f, 51f, 49f)),
                BuddyRangePoints("1M", listOf(70f, 67f, 64f, 61f, 58f, 55f, 53f, 50f, 48f)),
                BuddyRangePoints("3M", listOf(74f, 70f, 66f, 63f, 60f, 57f, 54f, 51f, 48f)),
                BuddyRangePoints("1Y", listOf(76f, 72f, 69f, 64f, 66f, 60f, 56f, 52f, 48f)),
                BuddyRangePoints("All", listOf(72f, 68f, 65f, 62f, 59f, 56f, 53f, 50f, 47f))
            ),
            holdingTitle = "65 Units",
            holdingCaption = "Avg NAV \u20B9281",
            holdingValue = "\u20B917816",
            holdingValueChange = "-2.45%",
            holdingValueTrend = BuddyTrend.NEGATIVE,
            ratings = listOf(
                BuddyRating("Overall", 6),
                BuddyRating("Technical", 6),
                BuddyRating("Financial", 7),
                BuddyRating("Industry", 6),
                BuddyRating("Advisors", 6)
            ),
            aiSuggestion = "Buddy.ai suggests to HOLD and avoid fresh concentration",
            aiSuggestionTrend = BuddyTrend.NEUTRAL,
            primaryAction = "SELL",
            secondaryAction = "BUY"
        ),
        "uti-nifty-mf" to BuddyEquityDetail(
            id = "uti-nifty-mf",
            name = "UTI Nifty",
            headlineValue = "\u20B9287.7",
            headlineChange = "-2.21%",
            headlineHorizon = "1D",
            ranges = listOf(
                BuddyRangePoints("1D", listOf(58f, 57f, 56f, 55f, 54f, 53f, 52f, 51f, 50f, 49f, 48f, 47f)),
                BuddyRangePoints("1W", listOf(62f, 61f, 59f, 57f, 55f, 53f, 51f, 49f, 47f)),
                BuddyRangePoints("1M", listOf(68f, 65f, 62f, 59f, 56f, 53f, 50f, 47f, 45f)),
                BuddyRangePoints("3M", listOf(72f, 68f, 64f, 61f, 58f, 54f, 51f, 48f, 45f)),
                BuddyRangePoints("1Y", listOf(74f, 69f, 66f, 62f, 65f, 58f, 54f, 49f, 45f)),
                BuddyRangePoints("All", listOf(70f, 66f, 63f, 60f, 56f, 53f, 50f, 47f, 44f))
            ),
            holdingTitle = "60 Units",
            holdingCaption = "Avg NAV \u20B9295",
            holdingValue = "\u20B917262",
            holdingValueChange = "-2.47%",
            holdingValueTrend = BuddyTrend.NEGATIVE,
            ratings = listOf(
                BuddyRating("Overall", 6),
                BuddyRating("Technical", 6),
                BuddyRating("Financial", 7),
                BuddyRating("Industry", 6),
                BuddyRating("Advisors", 6)
            ),
            aiSuggestion = "Buddy.ai suggests to HOLD and rebalance with profile goals",
            aiSuggestionTrend = BuddyTrend.NEUTRAL,
            primaryAction = "SELL",
            secondaryAction = "BUY"
        ),
        "sbi-bond" to BuddyBondDetail(
            id = "sbi-bond",
            name = "SBI Bond",
            headlineValue = "10% per annum",
            unitsValue = "100 units",
            avgPrice = "Avg Price \u20B9500",
            holdingValue = "\u20B950000",
            ratings = listOf(
                BuddyRating("Overall", 7),
                BuddyRating("Risk", 4),
                BuddyRating("Financial", 9),
                BuddyRating("Industry", 5)
            ),
            aiSuggestion = "Buddy.ai suggests to HOLD position analyzing your profile",
            aiSuggestionTrend = BuddyTrend.NEUTRAL,
            primaryAction = "SELL",
            secondaryAction = "BUY"
        ),
        "rbi-bond" to BuddyBondDetail(
            id = "rbi-bond",
            name = "RBI Bond",
            headlineValue = "11.2% per annum",
            unitsValue = "75 units",
            avgPrice = "Avg Price \u20B9610",
            holdingValue = "\u20B945750",
            ratings = listOf(
                BuddyRating("Overall", 8),
                BuddyRating("Risk", 5),
                BuddyRating("Financial", 9),
                BuddyRating("Industry", 6)
            ),
            aiSuggestion = "Buddy.ai suggests to HOLD with selective accumulation",
            aiSuggestionTrend = BuddyTrend.NEUTRAL,
            primaryAction = "SELL",
            secondaryAction = "BUY"
        ),
        "sun-pharma-bond" to BuddyBondDetail(
            id = "sun-pharma-bond",
            name = "Sun Pharma Bond",
            headlineValue = "12% per annum",
            unitsValue = "60 units",
            avgPrice = "Avg Price \u20B9790",
            holdingValue = "\u20B947400",
            ratings = listOf(
                BuddyRating("Overall", 8),
                BuddyRating("Risk", 5),
                BuddyRating("Financial", 8),
                BuddyRating("Industry", 7)
            ),
            aiSuggestion = "Buddy.ai suggests to BUY gradually for profile stability",
            aiSuggestionTrend = BuddyTrend.POSITIVE,
            primaryAction = "SELL",
            secondaryAction = "BUY"
        ),
        "gold-bond" to BuddyBondDetail(
            id = "gold-bond",
            name = "Gold Bond",
            headlineValue = "9% per annum",
            unitsValue = "80 units",
            avgPrice = "Avg Price \u20B9540",
            holdingValue = "\u20B943200",
            ratings = listOf(
                BuddyRating("Overall", 7),
                BuddyRating("Risk", 6),
                BuddyRating("Financial", 8),
                BuddyRating("Industry", 7)
            ),
            aiSuggestion = "Buddy.ai suggests to HOLD for defensive profile balance",
            aiSuggestionTrend = BuddyTrend.NEUTRAL,
            primaryAction = "SELL",
            secondaryAction = "BUY"
        )
    )

    fun itemsFor(category: BuddyAssetClass): List<BuddyMarketItem> =
        marketItems.filter { it.category == category }

    fun detailFor(id: String): BuddyDetailInfo? = detailLookup[id]

    fun isWatchlisted(id: String): Boolean = watchlistIds.contains(id)

    fun toggleWatchlist(id: String) {
        if (watchlistIds.contains(id)) {
            watchlistIds.remove(id)
        } else {
            watchlistIds.add(id)
        }
    }

    fun watchlistItems(): List<BuddyMarketItem> =
        marketItems.filter { watchlistIds.contains(it.id) }
}