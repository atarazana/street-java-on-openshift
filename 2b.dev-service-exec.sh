#!/bin/sh

export DB_USER=$(oc get secret/fruit-service-db-secret -o jsonpath='{.data.DB_USER}' | base64 -d)
export DB_PASSWORD=$(oc get secret/fruit-service-db-secret -o jsonpath='{.data.DB_PASSWORD}' | base64 -d)
export DB_NAME=$(oc get secret/fruit-service-db-secret -o jsonpath='{.data.DB_NAME}' | base64 -d)
export DB_HOST=localhost

mvn clean spring-boot:run -Dspring-boot.run.profiles=openshift -Popenshift -f fruit-service/pom.xml