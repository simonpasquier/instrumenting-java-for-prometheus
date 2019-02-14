This repository contains several examples demonstrating how to instrument Java applications for Prometheus.

Table of Contents
=================

* [Running the demo](#running-the-demo)
* [JMX exporter](#jmx-exporter)
* [client_java](#client_java)
* [MicroProfile Metrics](#microprofile-metrics)
* [Micrometer](#micrometer)
* [License](#license)

## Running the demo

All examples can be packaged as container images for easy deployment.

Create a shared network for all the containers.

```bash
docker network create prometheus
```

Start the applications.

```bash
(cd jmx_exporter && docker-compose up -d)
docker run --rm -d -p 8081:8080 --network prometheus --name client_java quay.io/simonpasquier/client_java
docker run --rm -d -p 8082:8080 --network prometheus --name mp_metrics quay.io/simonpasquier/mp_metrics
docker run --rm -d -p 8083:8080 --network prometheus --name micrometer quay.io/simonpasquier/micrometer
docker run --rm -d -v $PWD/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml -p 9090:9090 --network prometheus --name prometheus prom/prometheus:latest
```

Verify that everything is running fine.

```bash
docker ps
```

You can access the Prometheus UI at `http://localhost:9090/`.

You can generate some load

```bash
docker run --rm -d --network prometheus --name loadtest quay.io/simonpasquier/loadtest \
    -concurrent 10 -rate 5 \
    -uri http://client_java:8080/hello \
    -uri http://mp_metrics:8080/hello \
    -uri http://micrometer:8080/hello
```

## [JMX exporter](./jmx_exporter)

Retrieve the metrics exposed by the endpoint:

```bash
curl http://localhost:9098/metrics
```

In the Prometheus UI, query the percentage of CPU consumed by Kafka (including the exporter):

```
100 * rate(process_cpu_seconds_total{job="kafka"}[1m])
```

## [client_java](./client_java)

Retrieve the metrics exposed by the endpoint:

```bash
curl http://localhost:8081/metrics
```

In the Prometheus UI, query the resident memory consumed by the application:

```
process_resident_memory_bytes{job="web"}
```

In the Prometheus UI, query the total percentage of CPU consumed for all applications instrumented with the Prometheus libraries:

```
100 * sum by(job) (rate(process_cpu_seconds_total[1m]))
```

## [MicroProfile Metrics](./mp_metrics)

Retrieve the metrics exposed by the endpoint:

```bash
curl http://localhost:8082/metrics
```

Retrieve only the base metrics exposed by the endpoint (`base` can be replaced by `vendor` or `application`):

```bash
curl http://localhost:8082/metrics/base
```

Retrieve the metric metadata:

```bash
curl -H "Accept: application/json" -XOPTIONS http://localhost:8082/metrics
```

In the Prometheus UI, query the ratio of request failures:

```
100 * rate(application:requests_failed_total[1m]) / rate(application:requests_total[1m])
```

Query all the response time percentiles:

```
application:request_duration_seconds
```

Query the 99th-percentile response time:

```
application:request_duration_seconds{quantile="0.99"}
```

## [Micrometer](./micrometer)

Retrieve the metrics exposed by the endpoint:

```bash
curl http://localhost:8083/actuator/prometheus
```

In the Prometheus UI, query the rate of requests by status code for the `/hello` endpoint:

```
sum by (job,status) (rate(http_server_requests_seconds_count{uri="/hello"}[1m]))
```

Query the 99th-percentile response time for the all endpoints:

```
histogram_quantile(0.99, sum by (job,uri,le) (rate(http_server_requests_seconds_bucket[1m])))
```

Query the rate of request failures for the `/hello` endpoint:

```
100 * sum by (job) (rate(http_server_requests_seconds_count{uri="/hello",status=~"5.+"}[1m])) / sum by (job) (rate(http_server_requests_seconds_count{uri="/hello"}[1m]))
```

## License

Apache License 2.0, see [LICENSE](https://github.com/simonpasquier/instrumenting-java-for-prometheus/blob/master/LICENSE).
