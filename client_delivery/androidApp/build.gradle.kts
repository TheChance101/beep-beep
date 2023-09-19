plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":client_delivery:shared"))
                implementation ("com.google.accompanist:accompanist-webview:0.25.1")
            }
        }
    }
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "org.thechance.beepbeep"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("../../design_system/shared/src/commonMain/resources")
    packagingOptions.pickFirsts += "compose-multiplatform.xml"
    defaultConfig {
        applicationId = "org.thechance.beepbeep.delivery"
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
