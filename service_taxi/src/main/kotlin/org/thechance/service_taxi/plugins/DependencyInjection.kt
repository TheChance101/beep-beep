package org.thechance.service_taxi.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.thechance.service_taxi.di.kmongoModule
import org.thechance.service_taxi.di.useCasesModule

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(kmongoModule, useCasesModule)
    }
}