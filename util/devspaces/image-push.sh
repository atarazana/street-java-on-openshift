
#!/bin/sh

. ./image-env.sh

podman push ${REGISTRY}/${REGISTRY_USER_ID}/${IMAGE_NAME}:${IMAGE_VERSION}
