This example is a very simple application running on Thorntail that exposes a service on `http://localhost:8080`.

It offers 2 endpoints:

* `/hello` responding `200 OK` with `Hello from Thorntail!`. The endpoint
* adds a random delay to generate different response times. From time to time,
* it will fail with `500 SERVER UNAVAILABLE`.
* `/metrics` exposing the MicroProfile metrics with the Prometheus format.

## Running it

```bash
mvn thorntail:run
```

Visit `http://localhost:8080/hello` a couple of times. Then go to `http://localhost:8080/metrics` to view the metrics.

MicroProfile metrics encompass base, vendor and application metrics. Even
without instrumenting your code and simply adding the MP-Metrics fraction in
your project, you get useful metrics. If you want to access a subset of the
metrics, you can append `base`, `vendor` or `application` to the metrics
endpoint (eg `http://localhost:8080/metrics/application`).

In addition to the Prometheus format, MicroProfile can also return the metrics as JSON payload:

```bash
curl -H "Accept: application/json" http://localhost:8080/metrics
```

As well as metadata:

```bash
curl -H "Accept: application/json" -XOPTIONS http://localhost:8080/metrics
```
