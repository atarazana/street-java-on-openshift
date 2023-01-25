#!/bin/sh

echo "Starting the all-in-one Jaeger container "
podman run --rm --name jaeger \
-e COLLECTOR_OTLP_ENABLED=true \
-p 5775:5775/udp \
-p 6831:6831/udp \
-p 6832:6832/udp \
-p 5778:5778 \
-p 14268:14268 \
-p 16686:16686 \
-p 4317:4317 \
-p 4318:4318 \
quay.io/jaegertracing/all-in-one:1.35

# quay.io/jaegertracing/all-in-one:1.21.0

