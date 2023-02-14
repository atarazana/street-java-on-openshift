#!/bin/sh

. ./image-env.sh

podman run -it --rm -p 8080:8080 -e OTL_ENDPOINT=http://192.168.50.17:4317 -e FRUIT_SERVICE_URL=http://192.168.50.17:8081 -e JAVA_OPTS="-Dspring.profiles.active=openshift" $REGISTRY/$REGISTRY_USER_ID/${PROJECT_ID}-${ARTIFACT_ID}:${GIT_HASH}