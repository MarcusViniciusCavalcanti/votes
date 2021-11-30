FROM maven:3.6-openjdk-11-slim as api
WORKDIR /usr/src/api
COPY pom.xml .
RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY . .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM openjdk:11
RUN useradd -ms /bin/bash application
WORKDIR /backend

RUN chown application:application /backend
COPY --from=api /usr/src/api/target/osvaldo-0.0.1.jar .

ENTRYPOINT ["java", "-jar", "/backend/osvaldo-0.0.1.jar"]
CMD ["--spring.profiles.active=docker"]
