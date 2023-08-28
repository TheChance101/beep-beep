package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.domain.usecase.IManageRestaurantRequestUseCase

fun Route.restaurantPermissionRequestRoutes() {
    val manageRestaurantRequestUseCase: IManageRestaurantRequestUseCase by inject()

    route("/restaurant-permission-request") {
        get {
            val restaurantPermissionRequests = manageRestaurantRequestUseCase.getRestaurantRequests().toDto()
            call.respond(HttpStatusCode.OK, restaurantPermissionRequests)
        }

        post {
            val params = call.receiveParameters()
            val restaurantName = params["restaurantName"]?.trim().toString()
            val ownerEmail = params["ownerEmail"]?.trim().toString()
            val cause = params["cause"]?.trim().toString()
            val result = manageRestaurantRequestUseCase.createRestaurantRequest(restaurantName, ownerEmail, cause)
            call.respond(HttpStatusCode.Created, result.toDto())
        }
    }

}