#!/bin/bash
# check-keycloak-server-started.sh
echo "Check Keycloak Server Started:"
curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://keycloak-authorization-server:9091/auth/realms/microservices-realm)
echo "result status code: " $curlResult
while [[ ! $curlResult == "200" ]]; do
  >&2 echo "Keycloak Server is not up yet!"
  sleep 10
  curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://keycloak-authorization-server:9091/auth/realms/microservices-realm)
done

/cnb/process/web