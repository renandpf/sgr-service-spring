FROM maven:3.8.7-openjdk-18-slim AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package

FROM openjdk:18-jdk
ENV TZ America/Sao_Paulo
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]