#!/usr/bin/env bash
# Start script for Render

set -e

echo "🚀 Starting Spring Boot Application..."
java -Dserver.port=$PORT \
     -Dspring.profiles.active=prod \
     -jar target/api-server-1.0.0.jar
