#!/bin/sh

CURRENT_DIR=$(pwd)
cd $(dirname $0)

. ./image-env.sh

skopeo inspect docker://$REGISTRY/$REGISTRY_USER_ID/${PROJECT_ID}-${ARTIFACT_ID}:${ARTIFACT_VERSION} | jq -r .Digest

cd ${CURRENT_DIR}