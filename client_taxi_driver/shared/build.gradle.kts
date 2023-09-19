@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.native.cocoapods)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlinKsp)
    alias(libs.plugins.realm)
    alias(libs.plugins.kotlinx.serialization)
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = libs.versions.ios.deploymentTarget.get()
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                // design shared
                implementation(project(":design_system:shared"))
                // compose
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.components.resources)
                implementation(libs.google.accompanist)
                //ktor-client
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.json.serialization)
                implementation(libs.ktor.content.negotiation)
                implementation(libs.ktor.logging)
                implementation(libs.ktor.client.cio)
                implementation(libs.kotlin.serialization)
                //realm db
                implementation(libs.realm.library.base)
                // koin
                api(libs.koin.core)
                implementation(libs.koin.annotations)
                implementation(libs.koin.compose)
                // navigation voyager
                implementation(libs.bundles.voyager)
                // coroutine
                implementation(libs.kotlin.coroutines)
                //date time
                implementation(libs.kotlinx.datetime)

                api(libs.moko.geo)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.androidx.activity.compose)
                api(libs.androidx.appcompat)
                api(libs.androidx.core.ktx)
                api(libs.koin.android)
                implementation(libs.ktor.client.okhttp)
                implementation(libs.kotlin.coroutines)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.ktor.client.drawin)
            }
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(libs.versions.jvmToolchain.get().toInt())
    }
}
