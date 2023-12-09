#!/bin/bash

if command -v docker &> /dev/null ; then
    echo "Docker is installed."
    docker --version

    if command -v docker-compose &> /dev/null ; then
        echo "Docker Compose is installed."
        docker-compose --version

        echo "DOCKER START BUILDING CONTAINERS"

        sleep 3
        
        docker-compose build

        echo "START UP DOCKER CONTAINER"
        docker start mqtt_db
        docker start mqtt_mosquitto
        docker start mqtt_auth
        docker exec src-auth-1 bash bin/kc.sh import --file conf/realm-export.json
        docker start mqtt_resource
        docker start mqtt_frontend

        echo "DONE"

    else
        echo "Docker Compose is not installed. Please install Docker Compose before proceeding."
    fi
else
    echo "Docker is not installed. Please install Docker before proceeding."
fi



