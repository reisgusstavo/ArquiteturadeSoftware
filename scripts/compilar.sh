#!/bin/sh
cd "$(dirname "$0")/.."
mkdir -p out
javac -encoding UTF-8 -cp lib/conn.jar -d out $(find src -name "*.java")
