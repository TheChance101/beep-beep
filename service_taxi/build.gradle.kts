import groovy.xml.dom.DOMCategory.attributes
import org.jetbrains.kotlin.com.intellij.openapi.vfs.StandardFileSystems.jar

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val h2_version: String by project

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
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")

    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-swagger:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

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

}

