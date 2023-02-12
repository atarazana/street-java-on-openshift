# Install ArgoCD, Pipelines and Quay using the operators

Install ArgoCD Operator, Openshift Pipelines and Quay (simplified non-supported configuration):

```sh
until oc apply -k util/bootstrap/; do sleep 2; done
```