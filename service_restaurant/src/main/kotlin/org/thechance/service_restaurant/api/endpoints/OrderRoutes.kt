package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.OrderDto
import org.thechance.service_restaurant.api.models.RestaurantInfo
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.api.utils.closeSession
import org.thechance.service_restaurant.domain.usecase.IManageOrderUseCase
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND
import java.util.*
import java.util.concurrent.ConcurrentHashMap


private val openingRestaurants: ConcurrentHashMap<String, RestaurantInfo> = ConcurrentHashMap()
private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

fun Route.orderRoutes() {
    val manageOrder: IManageOrderUseCase by inject()

    route("restaurant/order") {

        get("/{id}") {
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val result = manageOrder.getOrderById(orderId = id)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        post("/{id}/status") {
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val status = call.receiveParameters()["status"]?.toInt() ?: throw MultiErrorException(
                listOf(INVALID_REQUEST_PARAMETER)
            )
            val result = manageOrder.updateOrderStatus(
                orderId = id,
                state = OrderStatus.getOrderStatus(status)
            )
            call.respond(HttpStatusCode.OK, result)
        }

        get("/history/{id}") {
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 10

            val result = manageOrder.getOrdersHistory(restaurantId = id, page = page, limit = limit)
            call.respond(HttpStatusCode.OK, result.map { it.toDto() })
        }

        webSocket("/{restaurantId}/{userId?}") {

            val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
            val userId = call.parameters["userId"]?.trim().orEmpty()

            if (userId.isEmpty()) {
                openingRestaurants[restaurantId] = RestaurantInfo(owner = this, mutableListOf())
                for (frame in incoming) {
                    return@webSocket
                }
            } else {
                openingRestaurants[restaurantId]?.users?.add(mutableMapOf(userId to this))
                broadcast(
                    receiveChannel = incoming,
                    restaurantId = restaurantId,
                    manageOrder = manageOrder
                )
            }
        }
    }
}

private suspend fun broadcast(
    receiveChannel: ReceiveChannel<Frame>,
    restaurantId: String,
    manageOrder: IManageOrderUseCase
) {
    val ownerSession = openingRestaurants[restaurantId]?.owner

    try {
        for (frame in receiveChannel) {
            if (frame is Frame.Text) {

                val order = createOrder(frame)
                val isOrderInserted: Boolean = insertOrder(order!!, manageOrder)

                if (isOrderInserted) {
                    ownerSession?.send(order.toString())
                }
            }
        }

    } catch (e: Exception) {
        ownerSession.closeSession(e)
        ownerSession?.send(Frame.Text("An error occurred: ${e.message}"))

    } finally {
        ownerSession.closeSession()
        openingRestaurants.remove(restaurantId)
    }
}

private fun createOrder(frame: Frame.Text): OrderDto? {
    val orderJson = frame.readText()
    return Json.decodeFromString<OrderDto>(orderJson)
        .copy(id = UUID.randomUUID().toString())
}

private suspend fun insertOrder(order: OrderDto, manageOrder: IManageOrderUseCase): Boolean {
    return scope.async {
        manageOrder.addOrder(order = order.toEntity())
    }.await()
}


