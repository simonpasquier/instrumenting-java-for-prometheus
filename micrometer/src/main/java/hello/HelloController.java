package hello;

import java.lang.Math;
import java.lang.RuntimeException;
import java.lang.Thread;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
    try {
      // Add some random delay and fail approximately 1 request out of 20.
      long rand = (long)(Math.random() * 1000);
      if (rand % 5 == 0) {
          throw new InterruptedException();
      }
      Thread.sleep((long)(Math.random() * 1000));
      return "Hello from Spring!";
    }
    catch(InterruptedException e) {
      throw new RuntimeException("something bad happened!");
    }
    }
}
