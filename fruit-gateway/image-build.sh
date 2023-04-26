
#!/bin/sh

CURRENT_DIR=$(pwd)
cd $(dirname $0)

. ./image-env.sh

./mvnw package

podman build -f src/main/docker/Dockerfile.jvm -t ${PROJECT_ID}-${ARTIFACT_ID}:${GIT_HASH} .

podman tag ${PROJECT_ID}-${ARTIFACT_ID}:${GIT_HASH} ${PROJECT_ID}-${ARTIFACT_ID}:${ARTIFACT_VERSION}

cd ${CURRENT_DIR}