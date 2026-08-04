# Journey Companion - Cross-Device Trip Assistant

A demo application showcasing Android's cross-device capabilities across Phone, Auto, Wear, and XR platforms.

## Overview

This project implements a "Journey Companion" trip assistant that demonstrates how Android apps can adapt to different device contexts:

- **Phone**: Rich planning and control center
- **Auto (Android Auto)**: Simplified driving-safe interface  
- **Wear OS**: Quick glanceable info and lightweight actions
- **XR**: Immersive 3D/spatial destination preview

## Project Structure

```
android-beyond/
├── shared/              # Common data models and repository
│   └── TripModels.kt    # Trip, TripStatus, TripRepository, SampleTrips
├── mobile/              # Phone app with Jetpack Compose UI
├── automotive/          # Android Auto experience
├── wear/                # Wear OS app
├── xr/                  # Android XR immersive experience
└── tv/                  # Android TV (optional extension)
```

## Key Features

### Shared Module (`shared/`)
- **Trip data class**: Contains all trip information (destination, ETA, weather, status, etc.)
- **TripStatus enum**: PLANNED, IN_PROGRESS, ARRIVED, CANCELLED
- **TripRepository**: In-memory repository for trip management
- **SampleTrips**: Sample data for demo purposes

### Mobile App (`mobile/`)
Built with Jetpack Compose and Material 3:
- Trip list view
- Create new trips
- Active trip card with ETA, distance, weather
- Start navigation button
- Mark as arrived functionality
- Real-time state updates

### Next Steps for Full Implementation

#### 1. Android Auto (`automotive/`)
- Implement `CarAppService`
- Create driving-safe templates showing:
  - Destination name
  - ETA
  - Simple "Start Navigation" button
- Follow Android Auto design guidelines (large text, minimal actions)

#### 2. Wear OS (`wear/`)
- Implement Wear Complications showing ETA
- Create Tile with trip status
- Add notifications with actions:
  - "Share ETA"
  - "Arrived"
  - "Mute alerts"
- Haptic feedback for important moments

#### 3. Android XR (`xr/`)
- Create immersive destination preview
- Show 3D model or 360° image of destination
- Floating info panels:
  - Destination name
  - ETA
  - Weather conditions
  - Points of interest

## Technical Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose (Mobile), Compose for Wear, Car App Library (Auto)
- **Architecture**: Repository pattern with shared state
- **Sync**: Currently in-memory (can be extended with Firebase/Room)

## Demo Flow (60-90 seconds)

1. **Open phone app**: "Let's plan a trip to the Science Museum"
2. **Show trip details**: Display ETA, weather, and trip status
3. **Start trip**: Tap "Start Navigation"
4. **Show Android Auto**: "In the car, the UI becomes simplified and driving-safe"
5. **Show Wear OS**: "The watch gives quick glanceable updates"
6. **Open XR**: "In XR, users can preview the destination immersively"

## Build Configuration Updates

All modules updated to:
- `compileSdk = 37` (required by androidx.core:core:1.19.0)
- `targetSdk = 37`
- Removed deprecated `org.jetbrains.kotlin.android` plugin (built into AGP 9.0+)

## Running the Demo

1. Sync Gradle files
2. Build and run the `mobile` module on a phone/emulator
3. For full demo, also deploy to:
   - Android Auto head unit or desktop head unit emulator
   - Wear OS watch or emulator
   - XR device or emulator with ARCore

## Future Enhancements

- Firebase integration for real-time cross-device sync
- Voice commands ("Start trip", "Navigate home")
- Live traffic updates
- Calendar-based trip suggestions
- Media playback continuation
- Trip history and statistics
- Share ETA functionality

## License

Demo project for educational purposes.
