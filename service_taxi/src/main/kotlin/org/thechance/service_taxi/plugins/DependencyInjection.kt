package org.thechance.service_taxi.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin
import org.thechance.service_taxi.di.AppModule
import org.thechance.service_taxi.di.kmongoModule
import org.thechance.service_taxi.di.mongoModule

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(AppModule().module, mongoModule)
    }
}