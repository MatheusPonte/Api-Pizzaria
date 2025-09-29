FROM registry.access.redhat.com/ubi9/openjdk-21-runtime

WORKDIR /opt/app

COPY target/*.jar app.jar

EXPOSE 8900
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]