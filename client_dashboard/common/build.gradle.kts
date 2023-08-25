@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlinKsp)
    id("org.openjfx.javafxplugin") version "0.0.14"
    id("io.realm.kotlin") version "1.10.2"
//    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
}

group = "org.thechance"
version = "1.0-SNAPSHOT"

kotlin {
    jvm("desktop") {
        withJava()
        jvmToolchain(libs.versions.jvmToolchain.get().toInt())
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.compose.runtime)
                api(libs.compose.foundation)
                api(libs.compose.material3)

                implementation(libs.voyager.navigator)
                implementation(libs.voyager.bottomsheet.navigator)
                implementation(libs.voyager.tab.navigator)
                implementation(libs.voyager.transitions)
                implementation(libs.voyager.koin)

                implementation(libs.kotlin.realm)
                implementation(libs.kotlin.coroutines)

                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.json.serialization)
                implementation(libs.kotlin.serialization)
                implementation(libs.ktor.content.negotiation)
                implementation(libs.ktor.logging)
                implementation(libs.ktor.gson)

                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.okhttp)

                api(libs.koin.core)
                implementation(libs.koin.annotations)
                implementation(libs.koin.ksp)
                implementation(project(":design_system:shared"))
                implementation(compose.desktop.currentOs)
                implementation ("com.google.accompanist:accompanist-webview:0.30.1")
                implementation("org.openjfx:javafx-web:16")
            }
        }
        val desktopMain by getting {
            dependencies {
                api(libs.compose.preview)
            }
        }
    }
}

javafx {
    version = "17"
    modules = listOf("javafx.controls", "javafx.swing", "javafx.web, "javafx.graphics")
}"