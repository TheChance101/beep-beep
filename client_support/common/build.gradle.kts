@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    id("io.realm.kotlin") version "1.10.2"
    kotlin("plugin.serialization") version "1.8.10"
}

group = "org.thechance"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    jvm("desktop") {
        jvmToolchain(libs.versions.jvmToolchain.get().toInt())
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.compose.runtime)
                api(libs.compose.foundation)
                api(libs.compose.material3)
                implementation(project(":design_system:shared"))

                implementation(libs.voyager.navigator)
                implementation(libs.voyager.bottomsheet.navigator)
                implementation(libs.voyager.tab.navigator)
                implementation(libs.voyager.transitions)
                implementation(libs.voyager.koin)
                implementation(libs.kotlinx.datetime)

                api(libs.kotlin.realm)
                implementation(libs.kotlin.coroutines)

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
                implementation(compose.desktop.currentOs)
            }
        }
        val desktopMain by getting {
            dependencies {
                api(libs.compose.preview)
            }
        }
    }
}

android {
    compileSdkVersion(libs.versions.compileSdk.get().toInt())
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(libs.versions.minSdk.get().toInt())
        targetSdkVersion(libs.versions.targetSdk.get().toInt())
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}