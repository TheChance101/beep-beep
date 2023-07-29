FROM openjdk:11 as builder


COPY . .

RUN ./gradlew buildFatJar

FROM openjdk:11

COPY --from=builder /build/libs/service_location.jar ./service_location.jar

CMD ["java", "-jar", "service_location.jar"]