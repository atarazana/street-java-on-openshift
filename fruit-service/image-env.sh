#!/bin/sh

export ARTIFACT_VERSION=$(mvn help:evaluate -Dexpression=project.version -q -DforceStdout)
export ARTIFACT_ID=$(mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout)
export GIT_HASH=$(git rev-parse HEAD)

export REGISTRY=quay.io
export REGISTRY_USER_ID=atarazana
export PROJECT_ID=street-java
export APP_NAME=${PROJECT_ID}-app

