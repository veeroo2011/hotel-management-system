FROM alpine:3.19
RUN apk add --no-cache openjdk17 bash curl
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk
ENV PATH="${JAVA_HOME}/bin:${PATH}"
COPY target/hotel-booking-0.0.1-SNAPSHOT.jar /app.jar
CMD ["java", "-jar", "/app.jar" ]