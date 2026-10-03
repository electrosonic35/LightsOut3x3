# Lights Out 3x3

A small, real Android app project using Kotlin and Jetpack Compose.

## Included

- 3x3 Lights Out board
- Correct toggle rules: selected cell + up/down/left/right
- Move counter
- New Puzzle
- Reset to the current puzzle's starting state
- Win detection
- Local persistence of puzzle, current board and moves
- Unit tests for core game logic

## Toolchain

- Android Gradle Plugin 9.4.0
- Gradle 9.6.0
- compileSdk / targetSdk 37
- Kotlin 2.2.10
- Jetpack Compose BOM 2026.09.00

## Open and run

Open the `LightsOut3x3` folder in Android Studio. Let Gradle sync, then run the `app` configuration on an emulator or connected Android phone.

To build from a terminal with the Android SDK configured:

`./gradlew assembleDebug`

The APK will be produced at `app/build/outputs/apk/debug/app-debug.apk`.
