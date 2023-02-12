
#!/bin/sh

. ./image-env.sh

./mvnw clean package -Popenshift -DskipTests

podman build -f src/main/docker/Dockerfile.jvm -t ${PROJECT_ID}-${ARTIFACT_ID}:${GIT_HASH} .