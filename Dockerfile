FROM maven:3.5.3-jdk-8 AS build

COPY . .
RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM java:8-jdk-alpine
RUN adduser -Dh /home/treeptik treeptik
COPY --from=build target/springboot.rest-0.0.1-SNAPSHOT.jar /home/treeptik/

USER treeptik
ENV HOME /home/treeptik

EXPOSE 8181
ENTRYPOINT [ "java", "-jar", "/home/treeptik/springboot.rest-0.0.1-SNAPSHOT.jar" ]
