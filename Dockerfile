FROM openjdk:17
WORKDIR /app
COPY target/milkstgo.jar /app
EXPOSE 8090
CMD ["java", "-jar", "milkstgo.jar"]