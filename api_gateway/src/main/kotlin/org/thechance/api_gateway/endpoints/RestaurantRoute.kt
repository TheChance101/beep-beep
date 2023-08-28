package org.thechance.api_gateway.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.api_gateway.data.mappers.toRestaurant
import org.thechance.api_gateway.endpoints.gateway.IRestaurantGateway
import org.thechance.api_gateway.endpoints.utils.extractLocalizationHeader
import org.thechance.api_gateway.endpoints.utils.respondWithResult
import java.util.*

fun Route.restaurantRoutes() {
    val restaurantGateway: IRestaurantGateway by inject()

    route("/restaurant") {
        get {
            val (language, countryCode) = extractLocalizationHeader()
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 20
            val restaurants = restaurantGateway.getRestaurants(page, limit, locale = Locale(language, countryCode))
            respondWithResult(HttpStatusCode.OK, restaurants.toRestaurant())
        }

        get("/{id}") {
            val (language, countryCode) = extractLocalizationHeader()
            val restaurantId = call.parameters["id"]?.trim().toString()
            val restaurant =
                restaurantGateway.getRestaurantInfo(locale = Locale(language, countryCode), id = restaurantId)
            respondWithResult(HttpStatusCode.OK, restaurant)
        }

    }
}