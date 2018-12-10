FROM java:openjdk-8-jdk

ADD build/libs/gs-rest-service-0.1.0.jar /opt/springboot.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/opt/springboot.jar"]
