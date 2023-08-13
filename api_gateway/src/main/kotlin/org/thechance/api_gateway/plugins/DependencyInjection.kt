package org.thechance.api_gateway.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.thechance.api_gateway.di.AppModule
import org.koin.ksp.generated.module

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(AppModule().module)
    }
}