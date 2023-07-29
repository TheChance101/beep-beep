FROM openjdk:11 as builder


COPY . .

RUN ./gradlew buildFatJar

FROM openjdk:11

COPY --from=builder /build/libs/api_gateway.jar ./api_gateway.jar

CMD ["java", "-jar", "api_gateway.jar"]