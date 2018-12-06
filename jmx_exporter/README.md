This example runs a single-node Kafka cluster with the [JMX exporter](https://github.com/prometheus/jmx_exporter) running as a Java agent.

The metrics exposed by the JMX exporter are defined in [config.yml](./config.yml) (taken from the JMX exporter repository).

## Running it

```bash
docker-compose up
```

The metrics endpoint is available at `http://localhost:9098/metrics`.
