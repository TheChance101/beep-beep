package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.MealDto
import org.thechance.service_restaurant.domain.usecase.IManageCartUseCase
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.api.models.mappers.toMeal

fun Route.cartRoutes() {
    val manageCart: IManageCartUseCase by inject()
    route("cart") {
        post("/{userId}/{quantity}") {
            val meal = call.receive<MealDto>()
            val userId = call.parameters["userId"]?.toString() ?: throw MultiErrorException(
                listOf(INVALID_REQUEST_PARAMETER)
            )
            val quantity = call.parameters["quantity"]?.toInt() ?: 1
            val result = manageCart.addMealToCart(meal = meal.toMeal(), userId = userId, quantity = quantity)
            call.respond(HttpStatusCode.Created, result)
        }
    }
}