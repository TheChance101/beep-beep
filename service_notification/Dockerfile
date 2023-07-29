FROM openjdk:11 as builder


COPY . .

RUN ./gradlew buildFatJar

FROM openjdk:11

COPY --from=builder /build/libs/service_notification.jar ./service_notification.jar

CMD ["java", "-jar", "service_notification.jar"]