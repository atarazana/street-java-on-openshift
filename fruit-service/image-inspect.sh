#!/bin/sh

. ./image-env.sh

skopeo inspect docker://$REGISTRY/$REGISTRY_USER_ID/${PROJECT_ID}-${ARTIFACT_ID}:${ARTIFACT_VERSION} | jq -r .Digest