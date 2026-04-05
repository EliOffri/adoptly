# Android Final Project — Dogs Adoption App

## Project Overview

Android native app for dog shelters. Fetches dog breed/info data from a public API using Retrofit,
stores it in a local Room database, and reflects changes in the UI via LiveData/ViewModel.

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
- Advanced feature: Camera (take photo of stray animal + location)

## Fragments (minimum 6)

1. HomeFragment — browsable list of adoptable dogs (Retrofit, RecyclerView)
2. BreedDetailFragment — detail view for a selected dog/breed
3. FavoritesFragment — locally saved favorites (Room, RecyclerView, add/edit/remove)
4. AdoptionFragment — start adoption procedure for a selected dog
5. DonationFragment — donate food/toys/blankets (form with input validation)
6. ReportStrayFragment — report a stray animal: take photo + attach GPS location (Camera + Location)

## API

Use the free Dog API: https://dog.ceo/dog-api/

- GET /breeds/list/all
- GET /breed/{breed}/images
- GET /breeds/image/random/{count}
  These count as 3 structurally different endpoints (not just query param changes).

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
- Repository pattern: one repo per data domain (DogsRepository, FavoritesRepository)
- No comments at all

```

```
