#!/bin/sh

FRUIT_SERVICE_URL=http://localhost:8081

mvn clean quarkus:dev -f fruit-gateway/pom.xml