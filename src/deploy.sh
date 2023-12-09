#!/bin/bash

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo "Error: Node.js is not installed. Please install Node.js before proceeding."
    exit 1
fi

echo "Node.js is installed."
# Check Node.js version
node --version

# Check if npm is installed
if ! command -v npm &> /dev/null; then
    echo "Error: npm is not installed. Please install npm before proceeding."
    exit 1
fi

echo "npm is installed."
# Check npm version
npm --version

if ! command -v docker &> /dev/null ; then
    echo "Docker is not installed. Please install Docker before proceeding."
    exit 1
fi

echo "Docker is installed."
docker --version


if ! command -v docker-compose &> /dev/null ; then
    echo "Docker Compose is not installed. Please install Docker Compose before proceeding."
    exit 1
fi

echo "Docker Compose is installed."
docker-compose --version

# Check if java is installed and its version is at least 17
if command -v java &> /dev/null; then
    java_version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
    
    if [[ "$java_version" < "17" ]]; then
        echo "Error: Java version 17 or higher is required."
        exit 1
    else
        echo "Java is installed. Version: $java_version"
    fi
else
    echo "Error: Java is not installed. Please install OpenJDK version 17 or higher."
    exit 1
fi

# Continue with the rest of your script here
echo "Dependencies meet requirements. Continue with the script."

work_dir=$(pwd)
echo "Build frontend"
cd $work_dir/mqtt-admin-ui
npm install
npm run build --prod

echo "Build resource server"
cd $work_dir/mqtt-admin-server
./gradlew bootJar

echo "DOCKER START BUILDING CONTAINERS"

sleep 3

docker-compose create
docker network create src_mqtt_network

echo "START UP DOCKER CONTAINER"
docker start mqtt_db
sleep 10
docker start mqtt_mosquitto
docker start mqtt_auth
sleep 5
docker exec mqtt_auth bash bin/kc.sh import --file conf/realm-export.json
docker start mqtt_resource
docker start mqtt_frontend

echo "DONE"