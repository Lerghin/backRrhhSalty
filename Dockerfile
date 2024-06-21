FROM amazoncorretto:17-alpine-jdk
##ARG JAR_FILE=target/dentalbricenio-0.0.1-SNAPSHOT.jar
##COPY ${JAR_FILE} app_dentalbricenio.jar

COPY target/SaltyRRHH-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080
