rootProject.name = "BeepBeep"

include(":client_end_user:androidApp")
include(":client_end_user:shared")

include(":client_taxi_driver:androidApp")
include(":client_taxi_driver:shared")

include(":client_restaurant:androidApp")
include(":client_restaurant:shared")

include(":client_delivery:androidApp")
include(":client_delivery:shared")

include(":client_dashboard")
include(":client_dashboard:desktop")
include("client_dashboard:common")

include(":client_support")
include(":client_support:desktop")
include("client_support:common")

include(":design_system")
include(":design_system:shared")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }

    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        val agpVersion = extra["agp.version"] as String
        val composeVersion = extra["compose.version"] as String

        kotlin("jvm").version(kotlinVersion)
        kotlin("multiplatform").version(kotlinVersion)
        kotlin("android").version(kotlinVersion)

        id("com.android.application").version(agpVersion)
        id("com.android.library").version(agpVersion)

        id("org.jetbrains.compose").version(composeVersion)
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
