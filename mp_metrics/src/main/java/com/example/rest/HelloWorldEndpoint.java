package com.example.rest;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/hello")
public class HelloWorldEndpoint {

  @GET
  @Produces("text/plain")
  @Counted(monotonic = true, name = "requestsTotal", absolute = true)
  @Timed(name = "requestDuration", absolute = true, unit=MetricUnits.SECONDS)
  public Response doGet() {
    return Response.ok("Hello from Thorntail!").build();
  }
}
