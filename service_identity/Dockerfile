FROM openjdk:11 as builder


COPY . .

RUN ./gradlew buildFatJar

FROM openjdk:11

COPY --from=builder /build/libs/service_identity.jar ./service_identity.jar

CMD ["java", "-jar", "service_identity.jar"]