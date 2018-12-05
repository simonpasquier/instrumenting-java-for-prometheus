package com.example.rest;

import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import java.lang.Math;
import java.lang.Thread;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.InternalServerErrorException;

@Path("/hello")
public class HelloWorldEndpoint {
  static final Counter requestsTotal = Counter.build()
    .name("rest_requests_total").help("Total number of requests.").register();
  static final Counter requestsFailedTotal = Counter.build()
    .name("rest_requests_failed_total").help("Total number of failed requests.").register();
  static final Histogram requestDuration = Histogram.build()
    .name("rest_requests_duration_seconds").help("Request latency in seconds.").register();

  @GET
  @Produces("text/plain")
  public Response doGet() {
    Histogram.Timer requestTimer = requestDuration.startTimer();
    requestsTotal.inc();
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
    } finally {
      requestTimer.observeDuration();
    }
  }
}
