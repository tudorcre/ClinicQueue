#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
BUILD_DIR="$ROOT_DIR/build"
MAIN_CLASSES="$BUILD_DIR/classes/main"

rm -rf "$BUILD_DIR"
mkdir -p "$MAIN_CLASSES"

find "$ROOT_DIR/src/main/java" -name "*.java" > "$BUILD_DIR/main-sources.txt"
javac -encoding UTF-8 -Xlint:all -Werror -d "$MAIN_CLASSES" @"$BUILD_DIR/main-sources.txt"

cat > "$BUILD_DIR/MANIFEST.MF" <<'MANIFEST'
Main-Class: ro.ulbsibiu.clinicqueue.Main
MANIFEST

jar cfm "$BUILD_DIR/clinicqueue.jar" "$BUILD_DIR/MANIFEST.MF" -C "$MAIN_CLASSES" .
echo "Build finalizat: $BUILD_DIR/clinicqueue.jar"
