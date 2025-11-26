#!/usr/bin/env -S bash -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

rm -rf escape_from_arcturus7 escape_from_arcturus7.zip

TMP_DIR="escape_from_arcturus7"
mkdir "$TMP_DIR"

for item in *; do
  case "$item" in
  "$TMP_DIR" | *.zip) ;; # skip output dirs/files
  target | project) ;;
  zip.sh) ;;
  *) cp -r "$item" "$TMP_DIR/" ;;
  esac
done

zip -X -r escape_from_arcturus7.zip escape_from_arcturus7

rm -rf escape_from_arcturus7

echo Success!
