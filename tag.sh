#!/bin/bash

set -e

REPO=${REPO:-quay.io/simonpasquier}

docker tag client_java:latest "$REPO/client_java:latest"
docker tag mp_metrics:latest "$REPO/mp_metrics:latest"
docker tag micrometer:latest "$REPO/micrometer:latest"
