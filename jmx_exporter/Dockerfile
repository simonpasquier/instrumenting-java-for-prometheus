FROM wurstmeister/kafka:2.12-2.1.0

COPY jmx_exporter.yml /jmx_exporter/config.yml
ADD https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.3.1/jmx_prometheus_javaagent-0.3.1.jar /jmx_exporter/jmx_prometheus_javaagent.jar
