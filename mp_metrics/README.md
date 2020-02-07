This example is a very simple [Quarkus](https://quarkus.io/) application that exposes a service on `http://localhost:8080/hello`.

It offers 2 endpoints:

* `/hello` responding `200 OK` with `Hello from Quarkus!`. The endpoint
* adds a random delay to generate different response times. From time to time,
* it will fail with `500 SERVER UNAVAILABLE`.
* `/metrics` exposing the MicroProfile metrics with the Prometheus format.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application is packageable using `./mvnw package`.
It produces the executable `restful-endpoint-1.0.0-SNAPSHOT-runner.jar` file in `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/restful-endpoint-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or you can use Docker to build the native executable using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your binary: `./target/restful-endpoint-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image-guide .

## Using it

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
