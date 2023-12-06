export SERVER_PORT=8081
export DB_HOST=db
export DB_PORT=3306
export DB_NAME=mqtt

export AUTH_SERVER_HOST="auth"
export AUTH_SERVER_PORT=8080
export MQTT_HOST=mosquitto

java -jar ./build/libs/mqtt-admin-server-0.0.1-SNAPSHOT.jar