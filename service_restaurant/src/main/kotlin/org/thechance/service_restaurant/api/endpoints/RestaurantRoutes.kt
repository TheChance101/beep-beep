package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.RestaurantDto
import org.thechance.service_restaurant.api.usecases.RestaurantUseCasesContainer
import org.thechance.service_restaurant.entity.toDto

fun Route.restaurantRoutes() {

    val useCase: RestaurantUseCasesContainer by inject()

    route("/restaurant") {

        get {
            val restaurants = useCase.getRestaurants().toDto()
            call.respond(HttpStatusCode.OK, restaurants)
        }

        get("/{id}") {
            val restaurantId = call.parameters["id"] ?: ""
            val restaurant = useCase.getRestaurantDetails(restaurantId).toDto()
            call.respond(HttpStatusCode.OK, restaurant)
        }

        post {
            val restaurant = call.receive<RestaurantDto>()
            val result = useCase.addRestaurant(restaurant.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        put {
            val restaurant = call.receive<RestaurantDto>()
            val result = useCase.updateRestaurant(restaurant.toEntity())
            call.respond(HttpStatusCode.OK, result)
        }

        delete("/{id}") {
            val restaurantId = call.parameters["id"] ?: ""
            val result = useCase.deleteRestaurant(restaurantId)
            call.respond(HttpStatusCode.OK, result)
        }

    }
}