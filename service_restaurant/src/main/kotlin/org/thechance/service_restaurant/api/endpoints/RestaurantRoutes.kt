package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.mappers.toRestaurantDto
import org.thechance.service_restaurant.datasource.RestaurantDataSource
import org.thechance.service_restaurant.domain.repository.BeepRepository
import org.thechance.service_restaurant.domain.usecase.CreateRestaurantUseCase
import org.thechance.service_restaurant.domain.usecase.GetRestaurantUseCase

fun Route.restaurantRoutes() {

    val getRestaurant: GetRestaurantUseCase by inject()
    val createRestaurant: CreateRestaurantUseCase by inject()


    route("/restaurant") {

        get {
            val restaurant = getRestaurant().toRestaurantDto()
            call.respond(HttpStatusCode.OK, restaurant)
        }

        post {
            val params = call.receiveParameters()
            val name = params["name"]?.trim().orEmpty()
            val result = createRestaurant(name = name)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}