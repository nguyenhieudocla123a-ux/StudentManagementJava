#!/usr/bin/env bash
# Build script for Render

set -e

echo "🔨 Building Spring Boot Application..."
mvn clean install -DskipTests

echo "✅ Build completed successfully!"
