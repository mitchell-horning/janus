FROM openjdk:8-alpine

COPY target/uberjar/janus.jar /janus/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/janus/app.jar"]
