plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
    id("com.google.gms.google-services") version "4.4.0"

}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":client_restaurant:shared"))
                //firebase
                implementation(libs.firebase.bom)
            }
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "org.thechance.beepbeep"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("../../design_system/shared/src/commonMain/resources")

    defaultConfig {
        applicationId = "org.thechance.beepbeep.restaurant"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(libs.versions.jvmToolchain.get().toInt())
    }
}
dependencies {
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation(libs.firebase.common.ktx)
    implementation(libs.firebase.messaging.ktx)
}
