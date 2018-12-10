#!/bin/bash

set -e

for dir in client_java mp_metrics; do
    pushd $dir
    mvn clean package
    docker build -t $dir:latest .
    popd
done

pushd micrometer
docker build -t micrometer:latest .
popd
