FROM maven:3-jdk-8

ENV PROJECT_DIR=/opt/library
RUN mkdir -p $PROJECT_DIR

WORKDIR ${PROJECT_DIR}
ADD pom.xml ${PROJECT_DIR}
ADD ./libs ${PROJECT_DIR}/libs
RUN mvn dependency:resolve

ADD .babelrc package.json webpack.config.js webpack.dev.config.js ${PROJECT_DIR}/
ADD ./src ${PROJECT_DIR}/src
RUN mvn package


FROM openjdk:8

ENV PROJECT_DIR=/opt/library
RUN mkdir -p $PROJECT_DIR

WORKDIR ${PROJECT_DIR}
COPY --from=0 ${PROJECT_DIR}/target/spring-2018-11_part2-1.0-SNAPSHOT.jar $PROJECT_DIR/library.jar

EXPOSE 8080

CMD ["java", "-jar", "library.jar"]
