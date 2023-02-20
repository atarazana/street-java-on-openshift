#!/bin/sh

CURRENT_DIR=$(pwd)
cd $(dirname $0)

. ./image-env.sh

DB_USER=$(oc get secret/fruit-service-db-secret -o jsonpath='{.data.DB_USER}' | base64 -d)
DB_PASSWORD=$(oc get secret/fruit-service-db-secret -o jsonpath='{.data.DB_PASSWORD}' | base64 -d)

podman run -it --rm -p 8081:8080 -e OTL_ENDPOINT=http://192.168.50.17:4317 -e DB_HOST=192.168.50.17 -e DB_NAME=FRUITSDB -e DB_USER=${DB_USER} -e DB_PASSWORD=${DB_PASSWORD} -e JAVA_OPTS="-Dspring.profiles.active=openshift" $REGISTRY/$REGISTRY_USER_ID/${PROJECT_ID}-${ARTIFACT_ID}:${GIT_HASH}

cd ${CURRENT_DIR}