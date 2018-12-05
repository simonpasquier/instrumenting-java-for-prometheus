package com.example.rest;

import io.prometheus.client.hotspot.DefaultExports;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartupListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent event) {
    // Register the process metrics.
    DefaultExports.initialize();
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
  }
}
