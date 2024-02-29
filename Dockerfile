FROM openjdk:8 AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:8
WORKDIR mybank
COPY --from=build target/*.jar mybank.jar
ENTRYPOINT ["java", "-jar", "mybank.jar"]