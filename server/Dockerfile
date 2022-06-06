FROM gradle:7-jdk8 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowJar --no-daemon

FROM openjdk:8
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/com.github.windsekirun.kokakunas.dashboard-*.jar /app/kohakunas-dashboard.jar
WORKDIR /app
CMD ["java", "-jar", "kohakunas-dashboard.jar"]