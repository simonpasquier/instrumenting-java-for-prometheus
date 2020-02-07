#!/bin/bash

set -e

pushd client_java
mvn clean package
docker build -t client_java:latest .
popd

pushd mp_metrics
./mvnw package -Pnative -Dquarkus.native.container-build=true
docker build -f src/main/docker/Dockerfile.native -t mp_metrics:latest .
popd

pushd micrometer
gradle assemble
docker build -t micrometer:latest .
popd
