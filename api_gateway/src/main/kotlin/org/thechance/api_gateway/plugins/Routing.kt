package org.thechance.api_gateway.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.thechance.api_gateway.data.model.TokenConfiguration
import org.thechance.api_gateway.endpoints.*

fun Application.configureRouting(tokenConfiguration: TokenConfiguration) {
    routing {
        userRoutes(tokenConfiguration)
        dashboardRoutes()
        orderRoutes()
        cuisineRoute()
        restaurantRoutes()
        taxiRoutes()
        mealRoute()
    }
}
