#!/bin/bash

set -e

REPO=${REPO:-quay.io/simonpasquier}

docker push "$REPO/client_java:latest"
docker push "$REPO/mp_metrics:latest"
docker push "$REPO/micrometer:latest"
