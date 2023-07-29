FROM openjdk:11 as builder


COPY . .

RUN ./gradlew buildFatJar

FROM openjdk:11

COPY --from=builder /build/libs/service_restaurant.jar ./service_restaurant.jar

CMD ["java", "-jar", "service_restaurant.jar"]