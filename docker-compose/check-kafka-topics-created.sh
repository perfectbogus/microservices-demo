#!/bin/bash
# check-kafka-topics-created.sh

apt-get update -y
yes | apt-get install kafkacat

kafkacatResult=$(kafkacat -L -b kafka-broker-1:9092)
echo "kafkacat results: " $kafkacatResult

while [[ ! $kafkacatResult == *"twitter-topic"* ]]; do
  >&2 echo "kafka topic has not been created yet!"
  sleep 15
  kafkacatResult=$(kafkacat -L -b kafka-broker-1:9092)
done

/cnb/process/web