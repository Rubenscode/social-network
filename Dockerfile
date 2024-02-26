FROM maven:3.9.2-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/arcane-*.jar arcane-*.jar

EXPOSE 8080

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "arcane-*.jar"]