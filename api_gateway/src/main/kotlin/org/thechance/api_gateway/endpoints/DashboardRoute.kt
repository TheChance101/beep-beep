package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Role
import java.util.*


fun Route.dashboardRoutes() {
    val restaurantGateway: IRestaurantGateway by inject()


    route("/dashboard") {

        authenticateWithRole(Role.DASHBOARD_ADMIN) {
            get("/restaurant-permission-request") {
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantGateway.getAllRequestPermission(Locale(language, countryCode))
                respondWithResult(HttpStatusCode.OK, result)
            }
        }
    }
}