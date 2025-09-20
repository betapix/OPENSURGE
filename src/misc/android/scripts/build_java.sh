#!/bin/bash
#
# Build scripts for the Android edition of the Open Surge Engine
# Copyright 2024-present Alexandre Martins <http://opensurge2d.org>
# License: GPL-3.0-or-later
#
# build_java.sh
# Build the Java part
#
set -e

echo "Building the Java part..."
source "$(dirname "$0")/set_sdk.sh" "$@"

# Extract classes.jar from the Allegro AAR
echo "Obtaining classes.jar from Allegro..."
pushd build/stage/libs

if [[ ! -e "allegro-release.aar" ]]; then
    echo "ERROR: can't find allegro-release.aar in the staging area"
    exit 1
fi

unzip -o allegro-release.aar classes.jar

popd

# Generate R.java
echo "Generating R.java..."
mkdir -p build/parts/java/build/src
"$SDK_BUILD_TOOLS/aapt" package -f \
    -m -J build/parts/java/build/src \
    -S build/parts/java/src/res \
    -M build/parts/java/src/AndroidManifest.xml \
    -I "$SDK_PLATFORM/android.jar"

# Download AdMob dependencies
echo "Downloading AdMob dependencies..."
mkdir -p build/stage/libs
cd build/stage/libs

# Download AdMob AAR
if [[ ! -e "play-services-ads-22.6.0.aar" ]]; then
    echo "Downloading play-services-ads-22.6.0.aar..."
    curl -L -o play-services-ads-22.6.0.aar "https://dl.google.com/dl/android/maven2/com/google/android/gms/play-services-ads/22.6.0/play-services-ads-22.6.0.aar"
fi

# Extract AdMob classes
unzip -o play-services-ads-22.6.0.aar classes.jar
mv classes.jar admob-classes.jar

cd ../../..

# Compile Java files
echo "Compiling Java files..."
"$JAVA_HOME/bin/javac" \
    -d build/parts/java/build/obj \
    -source 1.7 \
    -target 1.7 \
    -classpath "$SDK_PLATFORM/android.jar:build/stage/libs/classes.jar:build/stage/libs/admob-classes.jar" \
    -sourcepath build/parts/java/src/java/org/opensurge2d/surgeengine \
    build/parts/java/build/src/org/opensurge2d/surgeengine/R.java \
    build/parts/java/src/java/org/opensurge2d/surgeengine/*.java

# Generate the DEX bytecode
echo "Generating DEX bytecode..."
"$SDK_BUILD_TOOLS/d8" \
    --output build/parts/java/build \
    --lib "$SDK_PLATFORM/android.jar" \
    build/stage/libs/classes.jar \
    build/parts/java/build/obj/org/opensurge2d/surgeengine/*.class

# Success!
echo "The Java part has been built!"
