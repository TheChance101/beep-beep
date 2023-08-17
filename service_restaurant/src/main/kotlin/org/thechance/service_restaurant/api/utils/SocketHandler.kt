package org.thechance.service_restaurant.api.utils

import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.thechance.service_restaurant.api.models.OrderDto
import org.thechance.service_restaurant.api.models.RestaurantInfo
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.domain.usecase.IManageOrderUseCase
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class SocketHandler {
    val openedRestaurants: ConcurrentHashMap<String, RestaurantInfo> = ConcurrentHashMap()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    suspend fun broadcastOrder(
        receiveChannel: ReceiveChannel<Frame>,
        restaurantId: String,
        manageOrder: IManageOrderUseCase
    ) {
        val ownerSession = openedRestaurants[restaurantId]?.owner

        try {
            for (frame in receiveChannel) {
                if (frame is Frame.Text) {

                    val order = convertOrderFromTextFrameToOrderDto(frame = frame)
                    val isOrderInserted: Boolean =
                        insertOrder(order = order, manageOrder = manageOrder)

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
            openedRestaurants.remove(restaurantId)
        }
    }

    private fun convertOrderFromTextFrameToOrderDto(frame: Frame.Text): OrderDto {
        val orderJson = frame.readText()
        return Json.decodeFromString<OrderDto>(orderJson).copy(id = UUID.randomUUID().toString())
    }

    private suspend fun insertOrder(order: OrderDto, manageOrder: IManageOrderUseCase): Boolean {
        return scope.async {
            manageOrder.addOrder(order = order.toEntity())
        }.await()
    }
}