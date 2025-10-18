#!/usr/bin/env bash
set -euo pipefail

IMAGE_NAME="citizens-registry-service:latest"
DB_VOLUME_NAME="citizens_db_data"

echo "Building Docker image for citizens registry service..."
docker build -t ${IMAGE_NAME} .

echo "Creating persistent volume for MySql database..."
docker volume inspect ${DB_VOLUME_NAME} >/dev/null 2>&1 || docker volume create ${DB_VOLUME_NAME}

echo "Build was completed successfully."