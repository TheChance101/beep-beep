package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.BasePaginationResponseDto
import org.thechance.service_restaurant.api.models.WebSocketOrder
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.utils.SocketHandler
import org.thechance.service_restaurant.domain.usecase.IManageOrderUseCase
import org.thechance.service_restaurant.domain.usecase.IMangeCartUseCase
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND
import kotlin.collections.set

fun Route.orderRoutes() {
    val manageOrder: IManageOrderUseCase by inject()
    val manageCart: IMangeCartUseCase by inject()
    val socketHandler: SocketHandler by inject()

    route("/order") {

        get("/revenue-by-days-back") {
            val restaurantId = call.parameters["restaurantId"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val daysBack = call.parameters["daysBack"]?.toInt() ?: 7
            val result = manageOrder.getOrdersRevenueByDaysBefore(restaurantId = restaurantId, daysBack = daysBack)
            call.respond(HttpStatusCode.OK, result)
        }

        get("/count-by-days-back") {
            val restaurantId = call.parameters["restaurantId"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val daysBack = call.parameters["daysBack"]?.toInt() ?: 7
            val result = manageOrder.getOrdersCountByDaysBefore(restaurantId = restaurantId, daysBack = daysBack)
            call.respond(HttpStatusCode.OK, result)
        }

        get("/{id}") {
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val result = manageOrder.getOrderById(orderId = id)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        get("/restaurant/{restaurantId}/history") {
            val restaurantId = call.parameters["restaurantId"]?.trim() ?: throw MultiErrorException(listOf(NOT_FOUND))
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 10
            val result =
                manageOrder.getOrdersHistoryForRestaurant(restaurantId = restaurantId, page = page, limit = limit)
                    .map { it.toDto() }
            val total = manageOrder.getNumberOfOrdersHistoryInRestaurant(restaurantId)
            call.respond(
                HttpStatusCode.OK, BasePaginationResponseDto(items = result, page = page, total = total)
            )
        }

        get("/user/{userId}/history") {
            val userId = call.parameters["userId"]?.trim() ?: throw MultiErrorException(listOf(NOT_FOUND))
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 10
            val result =
                manageCart.getOrdersHistoryForUser(userId = userId, page = page, limit = limit).map { it.toDto() }
            val total = manageOrder.getNumberOfOrdersHistoryForUser(userId)
            call.respond(
                HttpStatusCode.OK, BasePaginationResponseDto(items = result, page = page, total = total)
            )
        }

        get("/{restaurantId}/orders") {
            val id = call.parameters["restaurantId"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val result = manageOrder.getActiveOrdersByRestaurantId(restaurantId = id)
            call.respond(HttpStatusCode.OK, result.map { it.toDto() })
        }

        get("/user/{userId}/orders") {
            val userId = call.parameters["userId"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val result = manageOrder.getActiveOrdersForUser(userId = userId)
            call.respond(HttpStatusCode.OK, result.map { it.toDto() })
        }

        get("/isExisted/{orderId}") {
            val orderId = call.parameters["orderId"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val result = manageOrder.isOrderExisted(orderId)
            call.respond(HttpStatusCode.OK, result)
        }

        put("/{id}") {
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val result = manageOrder.updateOrderStatus(orderId = id).toDto()
            socketHandler.orders[result.id]?.order?.emit(result)
            call.respond(HttpStatusCode.OK, result)
        }

        webSocket("/restaurant/{restaurantId}") {
            val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
            socketHandler.orders[restaurantId] = WebSocketOrder(this)
            socketHandler.collectOrder(restaurantId)
        }

        webSocket("/track/{orderId}") {
            val orderId = call.parameters["orderId"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            socketHandler.orders[orderId] = WebSocketOrder(this)
            socketHandler.collectOrder(orderId)
        }
    }
}