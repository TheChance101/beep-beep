package org.thechance.service_restaurant.plugins

import io.ktor.server.application.*
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin
import org.thechance.service_restaurant.di.AppModule
import org.thechance.service_restaurant.di.BeepClient

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(AppModule().module, BeepClient)
    }
}