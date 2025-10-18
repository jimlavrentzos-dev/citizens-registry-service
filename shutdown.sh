# Σταμάτημα και καταστροφή των containers, καταστροφή του Docker δικτύου, δεν καταστρέφεται το volume 'citizen-db-data'
#!/bin/bash
set -euo pipefail

NETWORK_NAME="citizen-registry-net"
DB_CONTAINER_NAME="citizen-db"
APP_CONTAINER_NAME="citizen-api"
IMAGE_NAME="citizens-registry-service:latest"


echo "Σταμάτημα και καταστροφή όλων των δοχείων που έχουν ξεκινήσει..."
docker stop ${APP_CONTAINER_NAME} ${DB_CONTAINER_NAME} 2>/dev/null
docker rm ${APP_CONTAINER_NAME} ${DB_CONTAINER_NAME} 2>/dev/null


echo "Καταστροφή του Docker δικτύου ${NETWORK_NAME}..."
docker network rm ${NETWORK_NAME} 2>/dev/null


echo "Η διαδικασία διακοπής και καθαρισμού των containers ολοκληρώθηκε.Το volume 'citizen-db-data' δεν έχει καταστραφεί, ώστε να μπορέσουν να διατηρηθούν τα δεδομένα του"
