package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.usecases.CreateRestaurantUseCase
import org.thechance.service_restaurant.api.usecases.GetRestaurantUseCase
import org.thechance.service_restaurant.api.usecases.RestaurantCasesContainer

fun Route.restaurantRoutes() {

    val restaurant: RestaurantCasesContainer by inject()

    route("/restaurant") {

        get {
            val restaurant = restaurant.getRestaurant().toDto()
            call.respond(HttpStatusCode.OK, restaurant)
        }

        post {
            val params = call.receiveParameters()
            val name = params["name"]?.trim().orEmpty()
            val result = restaurant.addRestaurant(name = name)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}