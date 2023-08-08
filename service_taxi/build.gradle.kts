val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val h2Version: String by project

plugins {
    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
    id("application")
}

group = "org.thechance.service_taxi"
version = "0.0.1"
application {
    mainClass.set("org.thechance.service_taxi.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
    fatJar {
        archiveFileName.set("service_taxi.jar")
    }
}

repositories {
    mavenCentral()
}


dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")

    implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-swagger:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    val kmongoVersion = "4.9.0"
    //KMongo
    implementation("org.litote.kmongo:kmongo:$kmongoVersion")

    //KMongo with coroutine
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:$kmongoVersion")

    //KMongo
    implementation("org.litote.kmongo:kmongo-id:$kmongoVersion")

    //Koin for ktor apps
    implementation("io.insert-koin:koin-ktor:3.4.1")

    //Koin
    implementation("io.insert-koin:koin-core:3.4.2")

    val koinKsp = "1.2.2"
    //Koin annotations
    implementation("io.insert-koin:koin-annotations:$koinKsp")

    //Koin KSP
    ksp("io.insert-koin:koin-ksp-compiler:$koinKsp")

    //Kotlin symbol processor
    implementation("com.google.devtools.ksp:symbol-processing-api:1.8.10-1.0.9")

    //Kotlinx Serialization for json
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.2.4")

    //kotlinx-datetime
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

}

