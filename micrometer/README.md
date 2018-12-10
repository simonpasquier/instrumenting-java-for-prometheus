This example is a very simple application running on Spring that exposes a service on `http://localhost:8080`. The application is instrumented using the [micrometer](http://micrometer.io/) library.

It offers 2 endpoints:

* `/hello` responding `200 OK` with `Hello from Micrometer!`. The endpoint
 adds a random delay to generate different response times. From time to time,
 it will fail with `500 SERVER UNAVAILABLE`.
* `/actuator/prometheus` exposing the Prometheus metrics.

## Running it

```bash
gradle runBoot
```

Visit `http://localhost:8080/hello` a couple of times. Then go to `http://localhost:8080/actuator/prometheus` to view the metrics.
