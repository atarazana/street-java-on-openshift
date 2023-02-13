#!/bin/sh

. ./image-env.sh

podman run -it --rm -p 8080:8080 -e DB_HOST=192.168.50.17 -e DB_NAME=FRUITSDB -e DB_USER=AjDIjvMMugnDWVTLCcb8r62bpQyoKg1t -e DB_PASSWORD=p1o6IgKiqih4MpxJ9eiAfDEeWXHz8W0M -e JAVA_OPTS="-Dspring.profiles.active=openshift" $REGISTRY/$REGISTRY_USER_ID/${PROJECT_ID}-${ARTIFACT_ID}:${GIT_HASH}