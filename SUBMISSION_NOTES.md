# Stockly — Submission Materials Reference

Use this document to fill in the required PPT presentation, poster, and project briefing form.

---

## App Summary

**App Name:** Stockly
**Purpose:** A real-time stock market portfolio tracker that lets users browse live market data, manage a personal watchlist, place simulated trade orders, set price alerts, and log trading decisions with photo and GPS location.
**Target Audience:** Individual retail investors and finance students who want to track stocks and practice trading decisions.

---

## Screen Flow (for flowchart / screenshots)

```
SplashActivity ("Get Started")
        │
        ▼
MainActivity (Bottom Nav)
  ├── Market (HomeFragment)
  │     └── tap stock → Stock Detail (breedDetailFragment)
  │                          └── "Trade" button → Trade (adoptionFragment)
  ├── Watchlist (favoritesFragment)
  ├── Alerts (donationFragment)
  └── Snapshot (reportStrayFragment)
```

**Fragment count:** 6 (Market, Stock Detail, Watchlist, Trade, Price Alerts, Market Snapshot)

---

## API Endpoints Used (Finnhub — https://finnhub.io/api/v1/)

| # | Endpoint | Method | Used In |
|---|----------|--------|---------|
| 1 | `/search?q={query}` | GET | Symbol search |
| 2 | `/stock/profile2?symbol={sym}` | GET | Company profile + logo |
| 3 | `/quote?symbol={sym}` | GET | Live price & change % |
| 4 | `/company-news?symbol={sym}&from={}&to={}` | GET | Recent news articles |

All 4 endpoints have structurally different paths (not just query param changes).

---

## Architecture Diagram (UML / Department Chart)

```
UI Layer
├── SplashActivity
├── MainActivity (NavHostFragment + BottomNav)
├── MarketFragment        ← MarketViewModel
├── StockDetailFragment   ← StockDetailViewModel
├── WatchlistFragment     ← WatchlistViewModel
├── TradeFragment         ← TradeViewModel
├── PriceAlertFragment    ← PriceAlertViewModel
└── MarketSnapshotFragment ← MarketSnapshotViewModel

ViewModel Layer
└── All ViewModels talk to repositories only

Repository Layer
├── StocksRepository      → Retrofit (Finnhub API)
├── WatchlistRepository   → Room (watchlist table)
└── UserStocksRepository  → Room (user_stocks table)

Data Layer
├── Remote: StockApiService (Retrofit + Gson)
│   Models: Stock, Quote, CompanyProfile, NewsItem, SearchResponse
└── Local: AppDatabase (Room v2)
    Entities: WatchlistEntity, UserStockEntity
    DAOs: WatchlistDao, UserStockDao

DI: Dagger-Hilt (NetworkModule, DatabaseModule)
```

---

## Key Methods / Features to Highlight

### 1. Combined local + remote stock list (MarketViewModel)
```kotlin
val combinedList = userStocksRepository.getAllUserStocks().asLiveData()
    .switchMap { userStocks ->
        _apiStocks.map { apiState ->
            // merges user-added stocks (Room) with Finnhub API stocks
        }
    }
```

### 2. Parallel API calls with coroutines (StocksRepository)
```kotlin
coroutineScope {
    val profileDeferred = async { apiService.getCompanyProfile(symbol, API_KEY) }
    val quoteDeferred   = async { apiService.getQuote(symbol, API_KEY) }
    Resource.Success(Pair(profileDeferred.await(), quoteDeferred.await()))
}
```

### 3. Swipe gestures on watchlist (WatchlistFragment)
- Swipe LEFT → delete with confirmation dialog
- Swipe RIGHT → edit notes dialog

### 4. Camera + GPS location (MarketSnapshotFragment)
- Full permission flow (rationale dialog, Settings redirect on permanent denial)
- FileProvider for secure camera URI
- Google FusedLocationProvider for last known location

### 5. Reactive Room with Flow (WatchlistDao)
```kotlin
@Query("SELECT EXISTS(SELECT 1 FROM watchlist WHERE symbol = :symbol)")
fun isInWatchlist(symbol: String): Flow<Boolean>
```
Used via `switchMap + asLiveData()` to reactively update the heart/favorite icon.

---

## Libraries / Dependencies Used

| Library | Purpose |
|---------|---------|
| Retrofit 2 + Gson | HTTP client + JSON parsing |
| OkHttp Logging Interceptor | Network debug logging |
| Room | Local SQLite database |
| Hilt (Dagger) | Dependency injection |
| Glide | Image loading (logos, news thumbnails) |
| Navigation Component | Fragment navigation + back stack |
| LiveData + ViewModel | MVVM reactive state management |
| Kotlin Coroutines + Flow | Async operations |
| Google Play Services Location | GPS coordinates |
| Material Components | UI (cards, chips, bottom sheets, dialogs) |

---

## Advanced Feature Used

**Camera + Location** (MarketSnapshotFragment)
- `ActivityResultContracts.TakePicture` for camera capture
- `FileProvider` for secure file URI sharing
- `FusedLocationProviderClient` for GPS coordinates
- Full permission flow: check → rationale → request → settings redirect on denial

---

## Code Requirements Compliance Checklist

| Requirement | Status |
|-------------|--------|
| Empty Views Activity template | ✅ |
| Visibility modifiers | ✅ All `private`/`internal` where appropriate |
| Input validation | ✅ TradeFragment, PriceAlertFragment, AddStockBottomSheet |
| Error handling | ✅ Resource.Error + Snackbar on all network/validation failures |
| Responsive UI | ✅ WindowInsets, ConstraintLayout throughout |
| Naming conventions | ✅ camelCase vars, PascalCase classes, snake_case XML IDs |
| ConstraintLayout (no heavy nesting) | ✅ |
| No SuppressLint / RequiresApi | ✅ |
| Full resource localization | ✅ strings.xml (English) |
| 6+ fragments | ✅ (Market, Detail, Watchlist, Trade, Alerts, Snapshot) |
| 3+ different API endpoints | ✅ (4 different paths) |
| Favorites / dynamic Room list (add/edit/remove) | ✅ Watchlist |
| 2+ RecyclerViews | ✅ StockAdapter (grid), WatchlistAdapter (list), NewsAdapter (horizontal) |
| Glide for images | ✅ |
| Hilt DI | ✅ |
| Navigation Component | ✅ |
| Coroutines | ✅ |
| LiveData + ViewModel | ✅ |
| Camera permission + location permission | ✅ |
| Custom launcher icon | ✅ |

---

## What You Need to Submit (and Who Does What)

### YOU must create (Claude cannot):
1. **APK file** — Build → Build Bundle(s)/APK(s) → Build APK(s) in Android Studio
2. **Presentation** (PPT + PDF) — Use the info above. Include screenshots of each screen.
3. **Poster** (PPT + PDF) — Use the poster template your professor provided, fill in with the info above.
4. **Video** (MP4) — Record the app running on your phone or emulator. Show all 6 fragments, adding a stock, watchlist add/edit/delete, trade flow, price alert, and taking a photo + location snapshot. Upload to YouTube and embed the link in your presentation.
5. **Project briefing form** (Word + PDF) — Use your professor's template. The architecture description, API used, and library list above covers everything needed.

### Submission folder name format:
`student_name1_student_name2_Stockly`

---

## Suggested Video Script (2-3 min)

1. Launch app → SplashActivity → tap "Get Started"
2. Market screen loads with live stock prices (show AAPL, TSLA, NVDA)
3. Tap a stock → Stock Detail (show price, change %, industry, news carousel)
4. Tap heart → added to watchlist (icon changes)
5. Tap "Trade" → enter 10 shares, select "Limit", enter price → Place Order
6. Navigate to Watchlist → show the added stock, swipe right to edit notes, swipe left to delete
7. Navigate to Alerts → enter AAPL, select "Above", enter 200 → Set Alert
8. Navigate to Snapshot → take photo → get location → write description → Save Snapshot
9. Back to Market → tap FAB (+) → add custom stock by symbol
