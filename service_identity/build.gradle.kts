val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version : String by project
val h2_version : String by project
val koin_version : String by project
val koin_ktor : String by project
val koin_ksp_version : String by project
val kmongo_version : String by project
val status_pages : String by project

plugins {

    kotlin("jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.4"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    id("application")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
}

group = "org.thechance.service_identity"
version = "0.0.1"
application {
    mainClass.set("org.thechance.service_identity.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
    fatJar {
        archiveFileName.set("service_identity.jar")
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

    // koin
    implementation("io.insert-koin:koin-ktor:$koin_ktor")
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-annotations:$koin_ksp_version")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.8.10-1.0.9")
    ksp("io.insert-koin:koin-ksp-compiler:$koin_ksp_version")

    // Kmongo
    implementation("org.litote.kmongo:kmongo:$kmongo_version")
    implementation("org.litote.kmongo:kmongo-coroutine:$kmongo_version")

    // Status pages error handler
    implementation("io.ktor:ktor-server-status-pages-jvm:$status_pages")

}

