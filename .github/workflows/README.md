# GitHub Actions Workflows

This directory contains GitHub Actions workflows for the Open Surge Engine project.

## Available Workflows

### 1. `android-build.yml` - Android Build Workflow
**Triggers:** Push to master/main, Pull requests, Manual dispatch

**Features:**
- Builds Android APKs for both Debug and Release
- Creates unsigned APK files
- Includes all game assets in the APK
- Supports ARM64 architecture
- Generates both debug and release builds

**Output:**
- Debug APK: `opensurge-android-debug.apk`
- Release APK: `opensurge-android-release.apk`
- Debug info and native libraries

## Usage

### Manual Android Build
To manually trigger an Android build:

1. Go to the Actions tab in your GitHub repository
2. Select "Android Build" workflow
3. Click "Run workflow"
4. Click "Run workflow" button

### Build Process
The workflow will:
1. Set up Android SDK and NDK
2. Build the native C++ library for ARM64
3. Create Android project structure
4. Package all game assets
5. Build both Debug and Release APKs
6. Upload APK artifacts

## Dependencies

The Android build requires:
- Android SDK and NDK
- Java 17
- CMake
- Build tools for ARM64 architecture

## Output Files

After successful build, you'll get:
- `opensurge-android-debug.apk` - Debug version (unsigned)
- `opensurge-android-release.apk` - Release version (unsigned)
- Debug info and native libraries

## Installation

To install the APK on your Android device:
1. Download the APK from the Actions artifacts
2. Enable "Install from unknown sources" in Android settings
3. Install the APK using a file manager or ADB

## Troubleshooting

### Common Issues

1. **Build Failures**
   - Check Android SDK/NDK installation
   - Verify CMake configuration
   - Ensure all game assets are present

2. **APK Installation Issues**
   - Make sure to enable unknown sources
   - Check device architecture (ARM64 required)
   - Verify APK file integrity

### Getting Help

If you encounter issues:
1. Check the Actions tab for detailed logs
2. Review the workflow configuration
3. Ensure all dependencies are properly set up
