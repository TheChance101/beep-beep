package org.thechance.api_gateway.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.thechance.api_gateway.data.security.TokenConfiguration
import org.thechance.api_gateway.endpoints.cuisineRoute
import org.thechance.api_gateway.endpoints.dashboardRoutes
import org.thechance.api_gateway.endpoints.restaurantRoutes
import org.thechance.api_gateway.endpoints.taxiRoutes
import org.thechance.api_gateway.endpoints.userRoutes
import org.thechance.api_gateway.endpoints.*

fun Application.configureRouting(tokenConfiguration: TokenConfiguration) {
    routing {
        userRoutes(tokenConfiguration)
        dashboardRoutes()
        restaurantRoutes()
        cuisineRoute()
        taxiRoutes()
        mealRoute()
    }
}
