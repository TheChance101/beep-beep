import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id( "org.openjfx.javafxplugin" ) version "0.0.13"
    id("io.realm.kotlin") version "1.10.2"
    kotlin("plugin.serialization") version "1.8.10"
}

group = "org.thechance"
version = "1.0-SNAPSHOT"


kotlin {
    jvm {
        jvmToolchain(libs.versions.jvmToolchain.get().toInt())
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":client_dashboard:common"))
                implementation(compose.desktop.currentOs)

            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "client_dashboard"
            packageVersion = "1.0.0"
        }
    }
}

javafx {
    version = "17"
    modules = listOf("javafx.controls", "javafx.swing", "javafx.web", "javafx.graphics")
}