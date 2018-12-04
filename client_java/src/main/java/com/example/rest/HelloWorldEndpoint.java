package com.example.rest;

import io.prometheus.client.Counter;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

@Path("/hello")
public class HelloWorldEndpoint {
  static final Counter requests = Counter.build()
    .name("rest_requests_total").help("Total requests.")
    .labelNames("method","handler").register();

  @GET
  @Produces("text/plain")
  public Response doGet() {
    requests.labels("GET","/hello").inc();
    return Response.ok("Hello from Thorntail!").build();
  }
}
