package com.example.metrics;

import io.prometheus.client.hotspot.DefaultExports;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletConfig;

@WebServlet("/metrics")
public class MetricsServlet extends io.prometheus.client.exporter.MetricsServlet {
    public void init(ServletConfig config) throws ServletException {
        // Register the process metrics.
        DefaultExports.initialize();
    }
}
