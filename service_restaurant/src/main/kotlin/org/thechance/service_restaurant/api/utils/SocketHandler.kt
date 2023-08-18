package org.thechance.service_restaurant.api.utils

import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json
import org.thechance.service_restaurant.api.models.OrderDto
import org.thechance.service_restaurant.api.models.RestaurantInfo
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.domain.usecase.IManageOrderUseCase
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class SocketHandler {
    val openedRestaurants: ConcurrentHashMap<String, RestaurantInfo> = ConcurrentHashMap()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    suspend fun broadcastOrder(
        receiveChannel: ReceiveChannel<Frame>,
        userId: String,
        restaurantId: String,
        manageOrder: IManageOrderUseCase
    ) {
        val ownerSession = openedRestaurants[restaurantId]?.owner
        val userSession = openedRestaurants[restaurantId]?.users?.firstOrNull { it.containsKey(userId) }?.get(userId)

        try {
            receiveChannel.consumeEach {
                if (it is Frame.Text) {

                    val order = createOrder(frame = it)
                    val isOpen = checkIfRestaurantOpened(restaurantId, manageOrder).await()
                    println("----mmm--- $isOpen")
                    val isOrderInserted: Boolean = insertOrder(order = order, manageOrder = manageOrder).await()

                    if (isOpen) {
                        if (isOrderInserted) ownerSession?.send(order.toString())
                    } else {
                        userSession?.send("Closed")
                        userSession?.close()
                    }
                } else {
                    userSession?.send("some thing error when u send an object")
                    userSession?.close()
                }
            }

        } catch (e: Exception) {
            userSession?.send("some thing error ${e.message}")
            userSession?.close()
        } finally {
        }
    }

    private fun createOrder(frame: Frame.Text): OrderDto {
        val orderJson = frame.readText()
        return Json.decodeFromString<OrderDto>(orderJson)
    }


    private suspend fun insertOrder(order: OrderDto, manageOrder: IManageOrderUseCase): Deferred<Boolean> {
        return scope.async {
            manageOrder.addOrder(order = order.copy(id = UUID.randomUUID().toString()).toEntity())
        }
    }

    private suspend fun checkIfRestaurantOpened(
        restaurantId: String,
        manageOrder: IManageOrderUseCase
    ): Deferred<Boolean> {
        return scope.async {
            manageOrder.isRestaurantOpened(restaurantId)
        }
    }
}