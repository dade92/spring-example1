#!/bin/bash

echo "Pulling the latest images..."
docker compose pull app

echo "Starting database..."
docker compose up -d mysql
sleep 4

echo "Starting external catalog..."
docker compose up -d external-catalog

echo "Starting backend app..."
docker compose up -d app
sleep 2

docker ps

echo "App seems up and running. Enjoy!"


