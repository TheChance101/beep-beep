@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    id("org.openjfx.javafxplugin") version "0.0.14"
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
                implementation(project(":design_system:shared"))
                implementation(compose.desktop.currentOs)
                implementation ("com.google.accompanist:accompanist-webview:0.30.1")
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