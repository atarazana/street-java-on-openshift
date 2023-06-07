#!/bin/sh

CURRENT_DIR=$(pwd)
cd $(dirname $0)

. ./image-env.sh

podman run -it --rm -p 8080:8080 -e FRUIT_GATEWAY_APP_NAME=fruit-gateway-user1 -e OTL_ENDPOINT=http://192.168.50.104:4317 -e FRUIT_SERVICE_URL=http://192.168.50.104:8081 --name gateway localhost/street-java-fruit-gateway:${ARTIFACT_VERSION}

cd ${CURRENT_DIR}