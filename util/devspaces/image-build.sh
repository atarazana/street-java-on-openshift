
#!/bin/sh

podman build -f Containerfile -t street-java-udi-rhel8:local .

podman tag street-java-udi-rhel8:local quay.io/atarazana/street-java-udi-rhel8:1.0.0
