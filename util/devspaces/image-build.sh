
#!/bin/sh

. ./image-env.sh

podman build -f Containerfile -t ${IMAGE_NAME}:local --build-arg FROM_IMAGE="${FROM_IMAGE}" .

podman tag ${IMAGE_NAME}:local ${REGISTRY}/${REGISTRY_USER_ID}/${IMAGE_NAME}:${IMAGE_VERSION}
