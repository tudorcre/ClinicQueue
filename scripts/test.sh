#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
BUILD_DIR="$ROOT_DIR/build"
MAIN_CLASSES="$BUILD_DIR/classes/main"
TEST_CLASSES="$BUILD_DIR/classes/test"

if [ ! -d "$MAIN_CLASSES" ]; then
  "$ROOT_DIR/scripts/build.sh"
fi

mkdir -p "$TEST_CLASSES"
find "$ROOT_DIR/src/test/java" -name "*.java" > "$BUILD_DIR/test-sources.txt"
javac -encoding UTF-8 -Xlint:all -Werror -cp "$MAIN_CLASSES" -d "$TEST_CLASSES" @"$BUILD_DIR/test-sources.txt"

java -cp "$MAIN_CLASSES:$TEST_CLASSES" ro.ulbsibiu.clinicqueue.AppointmentServiceTestRunner
