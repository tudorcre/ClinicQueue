#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
ANALYSIS_DIR="$ROOT_DIR/build/static-analysis"
rm -rf "$ANALYSIS_DIR"
mkdir -p "$ANALYSIS_DIR"

find "$ROOT_DIR/src" -name "*.java" > "$ANALYSIS_DIR/java-sources.txt"

# Analiză statică simplă: tratăm toate avertismentele javac ca erori.
javac -encoding UTF-8 -Xlint:all -Werror -d "$ANALYSIS_DIR/classes" @"$ANALYSIS_DIR/java-sources.txt"

# Reguli custom simple, utile într-un proiect educațional.
if grep -R "TODO\|FIXME" "$ROOT_DIR/src"; then
  echo "Static analysis failed: există TODO/FIXME în cod." >&2
  exit 1
fi

if awk 'length($0) > 120 { print FILENAME ":" FNR ": linie peste 120 caractere"; failed=1 } END { exit failed }' \
  $(find "$ROOT_DIR/src" -name "*.java"); then
  echo "Static analysis passed."
else
  echo "Static analysis failed: linii prea lungi." >&2
  exit 1
fi
