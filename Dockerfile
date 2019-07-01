FROM maven:3.5.3-jdk-8 AS build

COPY src/ src/
COPY pom.xml pom.xml

RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM java:8-jdk-alpine
RUN adduser -Dh /home/treeptik treeptik

COPY --from=build target/springboot.rest-0.0.1-SNAPSHOT.jar /home/treeptik/

ARG DB_USER="root"
ARG DB_PASS="pass"
ARG DB_URL="localhost:3306"
ARG DB_NAME="db_rest"

ENV \
    DB_URL=$DB_URL \
    DB_USER=$DB_USER \
    DB_PASS=$DB_PASS \
    DB_NAME=$DB_NAME

EXPOSE 8181
ENTRYPOINT [ "java", "-jar", "/home/treeptik/springboot.rest-0.0.1-SNAPSHOT.jar" ]
