#!/bin/sh

CURRENT_DIR=$(pwd)
cd $(dirname $0)

. ./image-env.sh

podman run -it --rm -p 8080:8080 -e OTL_ENDPOINT=http://192.168.50.105:4317 -e FRUIT_SERVICE_URL=http://192.168.50.105:8081 --name gateway $REGISTRY/$REGISTRY_USER_ID/${PROJECT_ID}-${ARTIFACT_ID}:${GIT_HASH}

cd ${CURRENT_DIR}