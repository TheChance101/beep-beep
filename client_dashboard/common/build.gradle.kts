@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlinKsp)
    id("org.openjfx.javafxplugin") version "0.0.14"
    id("io.realm.kotlin") version "1.10.2"
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
                implementation(libs.kotlinx.datetime)

                api(libs.kotlin.realm)
                implementation(libs.kotlin.coroutines)
                implementation("io.github.thechance101:chart:Beta-0.0.5")

                api(libs.ktor.client.core)
                api(libs.ktor.client.cio)
                api(libs.ktor.json.serialization)
                api(libs.kotlin.serialization)
                api(libs.ktor.content.negotiation)
                api(libs.ktor.logging)
                api(libs.ktor.gson)

                api(libs.ktor.client.cio)

                api(libs.koin.core)
                implementation(libs.koin.annotations)
                implementation(libs.koin.ksp)
                implementation("org.apache.pdfbox:pdfbox:2.0.26")

                implementation(project(":design_system:shared"))
                implementation(compose.desktop.currentOs)
                implementation("com.google.accompanist:accompanist-webview:0.30.1")
                implementation("org.openjfx:javafx-web:17")
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
    modules = listOf("javafx.controls", "javafx.swing", "javafx.web", "javafx.graphics")
}
