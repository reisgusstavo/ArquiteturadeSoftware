#!/bin/sh
cd "$(dirname "$0")/.."
java -cp out:lib/conn.jar Main
