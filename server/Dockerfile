FROM ghcr.io/graalvm/jdk:java17 AS build

RUN mkdir /appbuild
COPY . /appbuild
WORKDIR /appbuild

RUN mkdir ~/.gradle
RUN echo 'org.gradle.parallel=true' >> ~/.gradle/gradle.properties
RUN ./gradlew clean build --configure-on-demand --daemon

FROM ghcr.io/graalvm/jdk:java17

RUN mkdir /app

COPY --from=build /appbuild/build/libs/com.github.windsekirun.kokakunas.dashboard-*.jar /app/kohakunas-dashboard.jar
WORKDIR /app

CMD ["sh", "-c", "java -server -jar kohakunas-dashboard.jar"]