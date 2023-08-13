package org.thechance.api_gateway.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.thechance.api_gateway.di.ApiGatewayModule

fun Application.configureDependencyInjection() {
    install(Koin) {
        modules(ApiGatewayModule)
    }
}