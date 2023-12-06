docker-compose up --build -d
docker exec src-auth-1 bash bin/kc.sh import --file conf/realm-export.json
docker start src-resource-1