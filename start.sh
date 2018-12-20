#!/bin/bash

set -e

# Start the servers
(cd jmx_exporter && docker-compose up -d)
docker run --rm -d -p 8081:8080 --network prometheus --name client_java quay.io/simonpasquier/client_java
docker run --rm -d -p 8082:8080 --network prometheus --name mp_metrics quay.io/simonpasquier/mp_metrics
docker run --rm -d -p 8083:8080 --network prometheus --name micrometer quay.io/simonpasquier/micrometer
docker run --rm -d -v "$PWD"/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml -p 9090:9090 --network prometheus --name prometheus prom/prometheus:latest

# Start the load generator
docker run --rm -d --network prometheus --name loadtest quay.io/simonpasquier/loadtest \
    -concurrent 10 -rate 5 \
    -uri http://client_java:8080/hello \
    -uri http://mp_metrics:8080/hello \
    -uri http://micrometer:8080/hello

