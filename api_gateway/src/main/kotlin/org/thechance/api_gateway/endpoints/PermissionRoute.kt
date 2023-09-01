package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.model.restaurant.RestaurantRequestPermissionDto
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Role
import java.util.*

fun Route.permissionRoutes() {

    val restaurantService: RestaurantService by inject()

    route("/permission") {

        post("/restaurant") {
            val requestedForm = call.receive<RestaurantRequestPermissionDto>()
            val (language, countryCode) = extractLocalizationHeader()
            val result = restaurantService.createRequestPermission(
                requestedForm, Locale(language, countryCode)
            )
            respondWithResult(HttpStatusCode.Created, result)
        }

        authenticateWithRole(Role.DASHBOARD_ADMIN) {
            get("/restaurant-request") {
                val (language, countryCode) = extractLocalizationHeader()
                val result = restaurantService.getAllRequestPermission(Locale(language, countryCode))
                respondWithResult(HttpStatusCode.OK, result)
            }
        }
    }
}