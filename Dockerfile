FROM openjdk

WORKDIR /app

COPY target/testeblg-0.0.1-SNAPSHOT.jar /app/testeblg.jar

ENTRYPOINT ["java", "-jar", "testeblg.jar"]
