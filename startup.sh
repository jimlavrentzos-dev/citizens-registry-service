#Ρυθμίσεις MySQL περιβάλλοντος,δημιουργία του Docker Δικτύου (αν δεν υπάρχει ήδη),εκκίνηση backend υπηρεσίας και έκθεση της στη θύρα του host (8080:8080


APP_CONTAINER_NAME="citizen-api"


MYSQL_ROOT_PASSWORD="root_secure_password"
MYSQL_DATABASE_NAME="citizen_registry_db"
MYSQL_USER="registry_user"
MYSQL_PASSWORD="secure_password"
MYSQL_CONFIG_FILE="setup.sql" # Υποθετικό αρχείο SQL για setup χρήστη/βάσης


echo "Δημιουργία Docker δικτύου ${NETWORK_NAME}..."
docker network inspect ${NETWORK_NAME} >/dev/null 2>&1 || docker network create ${NETWORK_NAME}


echo "Εκκίνηση MySQL δοχείου MySQL (${DB_CONTAINER_NAME})..."
docker run -d \
    --name ${DB_CONTAINER_NAME} \
    --network ${NETWORK_NAME} \
    -v ${VOLUME_NAME}:/var/lib/mysql \
    -v $(pwd)/${MYSQL_CONFIG_FILE}:/docker-entrypoint-initdb.d/${MYSQL_CONFIG_FILE} \
    -e MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD} \
    -e MYSQL_DATABASE=${MYSQL_DATABASE_NAME} \
    -e MYSQL_USER=${MYSQL_USER} \
    -e MYSQL_PASSWORD=${MYSQL_PASSWORD} \
    ${DB_IMAGE}

if [ $? -ne 0 ]; then
    echo "Πρόβλημα κατά την διαδικασία εκκίνησης του δοχείου MySQL (${DB_CONTAINER_NAME})."
    exit 1
fi
echo "Το δοχείο MySQL (${DB_CONTAINER_NAME}) έχει εκκινηθεί επιτυχώς."


echo "Εκκίνηση του δοχείου της εφαρμογής RESTful API (${APP_CONTAINER_NAME})..."
docker run -d \
    --name ${APP_CONTAINER_NAME} \
    --network ${NETWORK_NAME} \
    -p 8080:8080 \
    -e SPRING_DATASOURCE_URL="jdbc:mysql://${DB_CONTAINER_NAME}:3306/${MYSQL_DATABASE_NAME}" \
    -e SPRING_DATASOURCE_USERNAME=${MYSQL_USER} \
    -e SPRING_DATASOURCE_PASSWORD=${MYSQL_PASSWORD} \
    ${APP_IMAGE}

if [ $? -ne 0 ]; then
    echo "Πρόβλημα κατά την εκκίνηση του δοχείου της εφαρμογής RESTful API (${APP_CONTAINER_NAME})."
    exit 1
fi
echo "Το δοχείο της εφαρμογής RESTful API (${APP_CONTAINER_NAME}) εκκίνησε επιτυχώς. Η υπηρεσία είναι διαθέσιμη στο http://localhost:8080"