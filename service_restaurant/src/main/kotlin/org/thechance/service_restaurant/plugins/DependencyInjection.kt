package org.thechance.service_restaurant.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin
import org.thechance.service_restaurant.di.BeepClient

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(BeepClient)
    }
}