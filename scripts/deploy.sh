#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
BUILD_DIR="$ROOT_DIR/build"
DEPLOY_DIR="$ROOT_DIR/deployment"

if [ ! -f "$BUILD_DIR/clinicqueue.jar" ]; then
  "$ROOT_DIR/scripts/build.sh"
fi

rm -rf "$DEPLOY_DIR"
mkdir -p "$DEPLOY_DIR"
cp "$BUILD_DIR/clinicqueue.jar" "$DEPLOY_DIR/clinicqueue.jar"
cat > "$DEPLOY_DIR/README_DEPLOYMENT.txt" <<'DEPLOYMENT'
Deployment demonstrativ pentru ClinicQueue.
Într-un proiect real, acest pas ar copia artefactul pe un server, într-un container sau într-un storage extern.
Pentru tema curentă, deployment-ul constă în copierea fișierului clinicqueue.jar în folderul deployment/.
Rulare: java -jar clinicqueue.jar
DEPLOYMENT

echo "Deployment finalizat în: $DEPLOY_DIR"
