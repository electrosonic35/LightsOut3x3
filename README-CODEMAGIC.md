# Lights Out 3x3 — Codemagic build

This project is configured for a cloud build with Codemagic.

## Codemagic steps

1. Put this project in a Git repository (GitHub is the simplest option).
2. In Codemagic, choose **Add application** and connect the repository.
3. Select **Android / native Android**.
4. Select the `android-native-debug` workflow from `codemagic.yaml`.
5. Start the build.
6. When successful, download `app-debug.apk` from the build artifacts.

The debug APK is intended for installation/testing on a personal Android phone; no release keystore is required for this first test.
