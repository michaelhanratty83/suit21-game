#!/usr/bin/env bash
set -e

cd "$(dirname "$0")"
export JAVA_HOME=/opt/homebrew/opt/openjdk@25
export PATH="$JAVA_HOME/bin:$PATH"

mvn clean compile -q
java -cp target/classes suit21.Suit21
