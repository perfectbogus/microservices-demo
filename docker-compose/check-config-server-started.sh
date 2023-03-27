#!/bin/bash
# check-config-server-started.sh
echo "Check Config Server Started"
apt-get update -y
yes | apt-get install curl
curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://config-server:8888/actuator/health)
echo "result status code: " $curlResult
while [[ ! $curlResult == "200" ]]; do
  >&2 echo "Config Server is not up yet!"
  sleep 10
  curlResult=$(curl -s -o /dev/null -I -w "%{http_code}" http://config-server:8888/actuator/health)
done

check-keycloak-server-started.sh