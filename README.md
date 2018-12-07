This repository contains several examples how to instrument Java applications for Prometheus.

* [Instrumenting with the JMX exporter](./jmx_exporter)
* [Instrumenting with the client_java library](./client_java)
* [Instrumenting with Metrics MicroProfile](./mp_metrics)

## Running the demo

All examples can be packaged as container images for easy deployment.

The first step is to build the `client_java` and `mp_metrics` images.

```bash
./build.sh
```

Then create a shared network for all the containers.

```bash
docker network create prometheus
```

Start the applications.

```bash
(cd jmx_exporter && docker-compose up -d)
docker run --rm -d -p 8081:8080 --network prometheus --name client_java client_java
docker run --rm -d -p 8082:8080 --network prometheus --name mp_metrics mp_metrics
docker run --rm -d -v $PWD/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml -p 9090:9090 --network prometheus --name prometheus prom/prometheus:latest
```

Verify that everything is running fine.

```bash
docker ps
```

You can access the Prometheus UI at `http://localhost:9090/`.

## License

Apache License 2.0, see [LICENSE](https://github.com/simonpasquier/instrumenting-java-for-prometheus/blob/master/LICENSE).
