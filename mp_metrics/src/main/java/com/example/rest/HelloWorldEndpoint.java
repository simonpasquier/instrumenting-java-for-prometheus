package com.example.rest;

import java.lang.Math;
import java.lang.Thread;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Produces;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/hello")
public class HelloWorldEndpoint {

  @Inject
  @Metric(absolute = true, description = "Total number of failed requests")
  Counter requestsFailedTotal;

  @GET
  @Produces("text/plain")
  @Counted(monotonic = true, name = "requestsTotal", absolute = true, description = "Total number of requests")
  @Timed(name = "requestDuration", absolute = true, unit=MetricUnits.SECONDS, description = "Request duration in seconds")
  public Response doGet() {
    try {
      // Add some random delay and fail approximately 1 request out of 20.
      long rand = (long)(Math.random() * 1000);
      if (rand % 50 == 0) {
          throw new InterruptedException();
      }
      Thread.sleep((long)(Math.random() * 1000));
      return Response.ok("Hello from Thorntail!").build();
    }
    catch(InterruptedException e) {
      requestsFailedTotal.inc();
      throw new InternalServerErrorException();
    }
  }
}
