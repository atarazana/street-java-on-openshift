
#!/bin/sh

. ./image-env.sh

podman build -f Containerfile -t ${IMAGE_NAME}:local --build-arg FROM_IMAGE="${FROM_IMAGE}" .

podman tag ${IMAGE_NAME}:local quay.io/atarazana/${IMAGE_NAME}:1.0.0
