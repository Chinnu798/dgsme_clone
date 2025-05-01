FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /dgsmeclone-0.0.1-SNAPSHOT/app


COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x ./mvnw
RUN ./mvnw install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM eclipse-temurin:17-jre-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Set environment variable for Railway.app
ENV PORT=8080

# Expose the port the app runs on
EXPOSE ${PORT}

ENTRYPOINT ["java","-cp","app:app/lib/*","gsmeclone-0.0.1-SNAPSHOT"]