package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.service.RestaurantService
import org.thechance.api_gateway.endpoints.utils.authenticateWithRole
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import org.thechance.api_gateway.util.Role


fun Route.cuisineRoute() {

    val restaurantService: RestaurantService by inject()

    route("/cuisine") {
        authenticateWithRole(Role.DASHBOARD_ADMIN) {
            post {
                val params = call.receiveParameters()
                val name = params["name"]?.trim().toString()
                val language = extractLocalizationHeader()
                val cuisine = restaurantService.addCuisine(name, language)
                respondWithResult(HttpStatusCode.Created, cuisine)
            }
        }

        get("/{id}/meals") {
            val language = extractLocalizationHeader()
            val cuisineId = call.parameters["id"]?.trim().toString()
            val meals = restaurantService.getMealsByCuisineId(
                cuisineId = cuisineId,
                languageCode = language
            )
            respondWithResult(HttpStatusCode.OK, meals)
        }

    }

    get("/cuisines") {
        val language = extractLocalizationHeader()
        val cuisines = restaurantService.getCuisines(languageCode = language)
        respondWithResult(HttpStatusCode.OK, cuisines)
    }
}

