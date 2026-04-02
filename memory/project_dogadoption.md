---
name: Dog Adoption Project Overview
description: Core details about the Adoptly Android app — architecture, design update, and key decisions
type: project
---

App is called **Adoptly** (renamed from "Dog Adoption" in strings.xml). Uses Dog CEO public API.

**Why:** Android final project requiring 6 fragments, Retrofit, Room, MVVM, Hilt, Coroutines.
**How to apply:** Respect all project rules in CLAUDE.md — no Compose, no hardcoded strings, no logic in Fragments, ConstraintLayout preferred.

## Architecture
- MVVM: Fragment → ViewModel → Repository → (Room / Retrofit)
- Hilt DI everywhere, single NavHost in MainActivity
- Bottom nav: Home, Saved (Favorites), Donate, Report Stray

## Design update (v3 — "Adoptly" design)
Applied the HTML mockup `adoptly_android_v3.html`. Key changes:
- Color palette: primary orange #F97316, background #F7F4F1, surface #FFFFFF, text #1C1917
- Removed the activity-level header; HomeFragment now owns the rich header (logo, greeting, headline, search bar, filter chips)
- No edge-to-edge (removed WindowCompat false call); status bar is white (#FFFFFF surface)
- New drawables: bg_logo_mark, bg_avatar, bg_chip_default/selected, bg_featured_badge/heart, bg_count_badge, ic_dog_paw, ic_search, ic_filter, ic_star
- fragment_home.xml: top_section (sticky white header) + NestedScrollView (featured card + breed grid)
- item_dog_breed.xml: white card with 18dp corners, 112dp image area, count badge overlay
- Chip toggle logic added to HomeFragment.setupChips()
- Featured card shows first breed from combined list (image loaded with Glide)

## Fragments
1. HomeFragment — breed grid + featured card (new Adoptly design)
2. BreedDetailFragment — hero image + gallery RecyclerView + Adopt button
3. FavoritesFragment — Room list, add/edit/remove favorites
4. AdoptionFragment — form (name, email, phone) with validation
5. DonationFragment — dropdown + quantity form
6. ReportStrayFragment — camera + GPS location
