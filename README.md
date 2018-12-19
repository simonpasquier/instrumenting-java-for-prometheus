This repository contains several examples how to instrument Java applications for Prometheus.

* [Instrumenting with the JMX exporter](./jmx_exporter)
* [Instrumenting with the client_java library](./client_java)
* [Instrumenting with Metrics MicroProfile](./mp_metrics)
* [Instrumenting with Micrometer](./micrometer)

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

You can generate some load:

```bash
docker run --rm -d --network prometheus --name loadtest quay.io/simonpasquier/loadtest \
    -concurrent 10 -rate 5 \
    -uri http://client_java:8080/hello \
    -uri http://mp_metrics:8080/hello \
    -uri http://micrometer:8080/hello
```

## License

Apache License 2.0, see [LICENSE](https://github.com/simonpasquier/instrumenting-java-for-prometheus/blob/master/LICENSE).
