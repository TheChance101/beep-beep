FROM openjdk:11 as builder


COPY . .

RUN ./gradlew buildFatJar

FROM openjdk:11

COPY --from=builder /build/libs/service_taxi.jar ./service_taxi.jar

CMD ["java", "-jar", "service_taxi.jar"]