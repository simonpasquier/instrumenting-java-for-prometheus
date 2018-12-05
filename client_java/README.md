This example is a very simple application running on Thorntail that exposes a service on `http://localhost:8080`. The application is instrumented using the [client_java](https://github.com/prometheus/client_java) library.

It offers 2 endpoints:

* `/rest/hello` responding `200 OK` with `Hello from Thorntail!`. The endpoint
* adds a random delay to generate different response times. From time to time,
* it will fail with `500 SERVER UNAVAILABLE`.
* `/metrics` exposing the Prometheus metrics.

## Running it

```bash
mvn thorntail:run
```

Visit `http://localhost:8080/rest/hello` a couple of times. Then go to `http://localhost:8080/metrics` to view the metrics.

