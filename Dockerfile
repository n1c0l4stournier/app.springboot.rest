FROM maven:3.5.3-jdk-8 AS build

COPY src/ src/
COPY pom.xml pom.xml

RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM java:8-jdk-alpine
RUN adduser -Dh /home/treeptik treeptik

COPY --from=build target/springboot.rest-0.0.1-SNAPSHOT.jar /home/treeptik/

ARG DB_USER_ARG=root
ENV DB_USER=$DB_USER_ARG

ARG DB_PASS_ARG=pass
ENV DB_PASS=$DB_PASS_ARG

ARG DB_URL_ARG="localhost:3306"
ENV DB_URL=$DB_URL_ARG

ARG DB_NAME_ARG="db_rest"
ENV DB_NAME=$DB_NAME_ARG

EXPOSE 8181
ENTRYPOINT [ "java", "-jar", "/home/treeptik/springboot.rest-0.0.1-SNAPSHOT.jar" ]
