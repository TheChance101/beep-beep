package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.utils.SocketHandler
import org.thechance.service_restaurant.api.utils.extractInt
import org.thechance.service_restaurant.api.utils.extractString
import org.thechance.service_restaurant.domain.usecase.IMangeCartUseCase
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND


fun Route.cartRoutes() {

    val manageCart: IMangeCartUseCase by inject()
    val socketHandler: SocketHandler by inject()

    route("/cart/{userId}") {

        get {
            val userId = call.parameters["userId"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val result = manageCart.getUserCart(userId)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        put {
            val userId = call.parameters["userId"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val restaurantId = call.parameters.extractString("restaurantId") ?: ""
            val mealId = call.parameters.extractString("mealId") ?: ""
            val quantity = call.parameters.extractInt("quantity") ?: 0
            val result = manageCart.updateMealInCart(userId, restaurantId, mealId, quantity)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        post("/orderNow") {
            val userId = call.parameters["userId"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val order = manageCart.orderCart(userId).toDto()
            socketHandler.orders[order.restaurantId]?.order?.emit(order)
            call.respond(HttpStatusCode.Created, order)
        }
    }
}