docker-compose -f docker-compose.yaml build --no-cache
docker-compose -f docker-compose.yaml up -d --build --force-recreate
docker ps -a
