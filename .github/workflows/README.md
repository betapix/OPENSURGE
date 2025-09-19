# GitHub Actions Workflows

This directory contains GitHub Actions workflows for the Open Surge Engine project.

## Available Workflows

### 1. `build.yml` - Main Build Workflow
**Triggers:** Push to master/main, Pull requests, Releases

**Features:**
- Builds the engine on Linux, Windows, and macOS
- Supports both Release and Debug builds
- Creates build artifacts for each platform
- Includes Android build support
- Automatically creates release packages when a new release is published

**Platforms:**
- Ubuntu (Linux)
- Windows (with vcpkg)
- macOS (with Homebrew)
- Android (ARM64)

### 2. `ci.yml` - Continuous Integration
**Triggers:** Push to master/main/develop, Pull requests

**Features:**
- Code formatting checks with clang-format
- CMake syntax validation
- Cross-platform build testing
- Security scanning
- Basic functionality tests

### 3. `release.yml` - Release Build
**Triggers:** Release published, Manual dispatch

**Features:**
- Creates optimized release builds
- Packages game assets with executables
- Creates platform-specific archives (.tar.gz for Linux/macOS, .zip for Windows)
- Uploads release assets to GitHub releases

### 4. `test.yml` - Test Suite
**Triggers:** Push to master/main/develop, Pull requests, Weekly schedule

**Features:**
- Unit tests and memory leak detection
- Integration tests with virtual display
- SurgeScript file validation
- Level and sprite file validation
- Performance benchmarks
- Compatibility tests across Ubuntu versions

### 5. `docs.yml` - Documentation
**Triggers:** Changes to README.md, docs/, or scripts/

**Features:**
- Generates API documentation with Doxygen
- Creates SurgeScript documentation
- Validates documentation files
- Spell checking for markdown files

## Usage

### Manual Release Build
To manually trigger a release build:

1. Go to the Actions tab in your GitHub repository
2. Select "Release Build" workflow
3. Click "Run workflow"
4. Enter the version number (e.g., 0.6.1.3)
5. Click "Run workflow"

### Local Development
To test workflows locally, you can use [act](https://github.com/nektos/act):

```bash
# Install act
curl https://raw.githubusercontent.com/nektos/act/master/install.sh | sudo bash

# Run a specific workflow
act -j build-linux

# Run all workflows
act
```

## Dependencies

The workflows require the following dependencies to be available:

### Linux (Ubuntu)
- cmake
- build-essential
- liballegro5-dev and related packages
- libphysfs-dev
- libsurgescript-dev

### Windows
- vcpkg for dependency management
- Visual Studio Build Tools

### macOS
- Homebrew
- cmake
- allegro, physfs, surgescript

## Configuration

### Environment Variables
- `GITHUB_TOKEN`: Automatically provided by GitHub Actions
- `VERSION`: Set automatically from release tags or manual input

### Secrets
No additional secrets are required for basic functionality. The workflows use the default `GITHUB_TOKEN` provided by GitHub Actions.

## Troubleshooting

### Common Issues

1. **Build Failures**
   - Check if all dependencies are properly installed
   - Verify CMake configuration
   - Check for missing submodules

2. **Test Failures**
   - Ensure test files are properly formatted
   - Check for memory leaks with valgrind
   - Verify SurgeScript syntax

3. **Release Issues**
   - Ensure version numbers are properly formatted
   - Check that all required files are present
   - Verify GitHub token permissions

### Getting Help

If you encounter issues with the workflows:

1. Check the Actions tab for detailed logs
2. Review the workflow files for configuration issues
3. Ensure all dependencies are properly specified
4. Check the Open Surge Engine documentation

## Contributing

When adding new workflows or modifying existing ones:

1. Test workflows locally with `act` if possible
2. Ensure workflows are properly documented
3. Use consistent naming conventions
4. Include appropriate error handling
5. Update this README with any new workflows or changes
