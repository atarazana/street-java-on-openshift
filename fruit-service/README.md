
oc new-app -e POSTGRESQL_USER=luke -ePOSTGRESQL_PASSWORD=secret -ePOSTGRESQL_DATABASE=FRUITSDB centos/postgresql-10-centos7 --as-deployment-config=false --name=postgresql-db
oc label dc/postgresql-db app.kubernetes.io/part-of=fruit-service-app  && \
oc label dc/postgresql-db app.openshift.io/runtime=postgresql --overwrite=true 

mvn clean oc:deploy -DskipTests -Popenshift