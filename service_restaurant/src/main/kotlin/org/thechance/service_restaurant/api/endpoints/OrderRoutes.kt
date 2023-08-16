package org.thechance.service_restaurant.api.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject
import org.thechance.service_restaurant.api.models.OrderDto
import org.thechance.service_restaurant.api.models.mappers.toDto
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.domain.usecase.IManageOrderUseCase
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap


// <restaurantId, list<Any User Need to Order>
private val openingRestaurants: ConcurrentHashMap<String, RestaurantInfo> = ConcurrentHashMap()
private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

fun Route.orderRoutes() {
    val manageOrder: IManageOrderUseCase by inject()

    route("restaurant/order") {

        get("/{id}") {
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val result = manageOrder.getOrderById(orderId=id)
            call.respond(HttpStatusCode.OK, result.toDto())
        }

        post("/{id}/status") {
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val status = call.receiveParameters()["status"]?.toInt() ?:
            throw MultiErrorException(listOf(INVALID_REQUEST_PARAMETER))
            val result = manageOrder.updateOrderStatus(
                orderId=id,
                state= OrderStatus.getOrderStatus(status))
            call.respond(HttpStatusCode.OK, result)
        }

        get("/history/{id}") {
            val id = call.parameters["id"] ?: throw MultiErrorException(listOf(NOT_FOUND))
            val page = call.parameters["page"]?.toInt() ?: 1
            val limit = call.parameters["limit"]?.toInt() ?: 10

            val result = manageOrder.getOrdersHistory(restaurantId=id, page=page, limit=limit)
            call.respond(HttpStatusCode.OK, result.map { it.toDto() })
        }

        webSocket("/{restaurantId}/{userId?}") {

            val restaurantId = call.parameters["restaurantId"]?.trim().orEmpty()
            val userId = call.parameters["userId"]?.trim().orEmpty()

            if (userId.isEmpty()) {
                println("I'm restaurant owner $restaurantId")
                    openingRestaurants[restaurantId] = RestaurantInfo(owner = this, mutableListOf())
                    broadcast(
                        receiveChannel = this.incoming,
                        restaurantId = restaurantId,
                        openingRestaurants = openingRestaurants,
                        manageOrder = manageOrder
                    )
            } else {
                println("I'm user $userId")
                openingRestaurants[restaurantId]?.users?.add(mutableMapOf(userId to this))
                broadcast(
                    receiveChannel = this.incoming,
                    isUser = true,
                    restaurantId = restaurantId,
                    openingRestaurants = openingRestaurants,
                    manageOrder = manageOrder
                )
            }
        }
    }
}

private suspend fun broadcast(
    receiveChannel: ReceiveChannel<Frame>,
    isUser: Boolean = false,
    restaurantId: String,
    openingRestaurants: ConcurrentHashMap<String, RestaurantInfo>,
    manageOrder: IManageOrderUseCase
) {
    try {
        val ownerSession = openingRestaurants[restaurantId]?.owner

        for (frame in receiveChannel) {
            if (frame is Frame.Text) {
                if (isUser) {
                    val orderId: UUID = UUID.randomUUID()
                    val orderJson = frame.readText()
                    val order = Json.decodeFromString<OrderDto>(orderJson)
                    ownerSession?.send(order.copy(id = orderId.toString()).toString())
                    scope.launch {
                        manageOrder.addOrder(order=order.copy(id = orderId.toString()).toEntity())
                    }
                }
            }
        }
    } catch (e: Exception) {
//        defaultWebSocketServerSession.close(CloseReason(CloseReason.Codes.NORMAL, "there is a problem"))
    } finally {

    }
}


data class RestaurantInfo(
    val owner: WebSocketSession, // owner session
    val users: MutableList<MutableMap<String, WebSocketSession>> // userid, session
)
