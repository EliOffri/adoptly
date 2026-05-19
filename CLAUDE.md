# Android Final Project — Stockly (Stock Market Tracker)

## Project Overview

Android native app for tracking stock market data. Fetches stock quotes and company information
from the Finnhub API using Retrofit, stores watchlist and user-added stocks in a local Room
database, and reflects changes in the UI via LiveData/ViewModel.

## Architecture

- Strict MVVM: UI (Fragment/Activity) → ViewModel → Repository → (Room DB / Retrofit API)
- No logic in Fragments or Activities. No logic in the data layer.
- Hilt for all dependency injection — no manual instantiation of ViewModels, Repos, or DAOs.

## Tech Stack

- Language: Kotlin only
- UI: XML layouts + View Binding. NO Jetpack Compose. NO data binding.
- Navigation: Jetpack Navigation Component (single NavHost in MainActivity)
- Network: Retrofit + Gson converter
- Images: Glide only (no Picasso, no Coil)
- Local DB: Room with DAO + Entity + Database class
- Async: Kotlin Coroutines + Flow/LiveData
- DI: Dagger-Hilt
- Advanced feature: Camera (take a market snapshot + location)

## Fragments (minimum 6)

1. MarketFragment — browsable list of stocks (Retrofit, RecyclerView)
2. StockDetailFragment — detail view for a selected stock
3. WatchlistFragment — locally saved watchlist (Room, RecyclerView, add/edit/remove)
4. TradeFragment — place a trade order for a selected stock
5. PriceAlertFragment — set price alerts (form with input validation)
6. MarketSnapshotFragment — capture a market moment: take photo + attach GPS location (Camera + Location)

## API

Use the Finnhub Stock API: https://finnhub.io/api/v1/

- GET /search
- GET /stock/profile2
- GET /quote
- GET /company-news
  These count as 4 structurally different endpoints.

## Rules — STRICTLY FOLLOW

- Start from Empty Views Activity template only
- All classes/functions/variables must have correct visibility modifiers
- Validate all user input before use
- Handle ALL errors — no unhandled exceptions, no crashes
- No SupressLint or RequiresApi annotations to silence warnings
- No hardcoded strings — all in strings.xml (full resource localization)
- No nested layouts that hurt performance — use ConstraintLayout
- Each RecyclerView item must show an image (loaded with Glide)
- Must use Bundles or ViewModel (never direct fragment-to-fragment calls) for data sharing
- App must have a custom launcher icon
- Handle permission flows completely (camera, location — including user denial case)

## Code Style

- Naming: camelCase for variables/functions, PascalCase for classes, snake_case for XML IDs
- No unused imports, no TODO comments left in final code
- Repository pattern: one repo per data domain (StocksRepository, WatchlistRepository)
- No comments at all

```

```
