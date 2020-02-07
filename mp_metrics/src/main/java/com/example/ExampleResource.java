package com.example;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.InternalServerErrorException;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/hello")
public class ExampleResource {
  @Inject
  @Metric(absolute = true, description = "Total number of failed requests")
  Counter requestsFailedTotal;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Counted(name = "requestsTotal", absolute = true, description = "Total number of requests")
  @Timed(name = "requestDuration", absolute = true, unit=MetricUnits.SECONDS, description = "Request duration in seconds")
  public String hello() {
    try {
      // Add some random delay and fail approximately 1 request out of 20.
      long rand = (long)(Math.random() * 1000);
      if (rand % 50 == 0) {
          throw new InterruptedException();
      }
      Thread.sleep((long)(Math.random() * 1000));
      return "Hello from Quarkus!";
    }
    catch(InterruptedException e) {
      requestsFailedTotal.inc();
      throw new InternalServerErrorException();
    }
  }
}
