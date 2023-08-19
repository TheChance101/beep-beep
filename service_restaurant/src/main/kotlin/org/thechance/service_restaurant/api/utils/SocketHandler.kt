package org.thechance.service_restaurant.api.utils

import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.thechance.service_restaurant.api.models.OrderDto
import org.thechance.service_restaurant.api.models.RestaurantInfo
import org.thechance.service_restaurant.api.models.mappers.toEntity
import org.thechance.service_restaurant.domain.usecase.IManageOrderUseCase
import org.thechance.service_restaurant.domain.utils.exceptions.INSERT_ORDER_ERROR
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_RECEIVED_FRAMES
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.RESTAURANT_CLOSED
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
        val userSession =
            openedRestaurants[restaurantId]?.users?.firstOrNull { it.containsKey(userId) }
                ?.get(userId)

        try {
            receiveChannel.consumeEach { frame ->

                if (frame is Frame.Text) {

                    val isOpen = ifRestaurantOpened(restaurantId, manageOrder).await()

                    if (isOpen) {
                        val order = createOrder(frame = frame)
                        val isOrderInserted: Boolean =
                            insertOrder(order = order, manageOrder = manageOrder).await()

                        if (isOrderInserted) {
                            ownerSession?.send(order.toString())
                            userSession?.close()
                        } else throw MultiErrorException(listOf(INSERT_ORDER_ERROR))

                    } else {
                        throw MultiErrorException(listOf(RESTAURANT_CLOSED))
                    }

                } else {
                    throw MultiErrorException(listOf(INVALID_RECEIVED_FRAMES))
                }
            }

        } catch (e: MultiErrorException) {
            userSession?.send(e.errorCodes.toString())
            userSession?.close()
        }
    }

    private fun createOrder(frame: Frame.Text): OrderDto {
        val orderJson = frame.readText()
        return Json.decodeFromString<OrderDto>(orderJson).copy(id = UUID.randomUUID().toString())
    }


    private suspend fun insertOrder(
        order: OrderDto,
        manageOrder: IManageOrderUseCase
    ): Deferred<Boolean> {
        return scope.async { manageOrder.addOrder(order = order.toEntity()) }
    }

    private suspend fun ifRestaurantOpened(
        restaurantId: String,
        manageOrder: IManageOrderUseCase
    ): Deferred<Boolean> {
        return scope.async { manageOrder.isRestaurantOpened(restaurantId) }
    }
}