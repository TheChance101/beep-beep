package org.thechance.service_restaurant.api.utils

import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.drop
import org.thechance.service_restaurant.api.models.RestaurantInfo
import org.thechance.service_restaurant.domain.usecase.IManageOrderUseCase
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import java.util.concurrent.ConcurrentHashMap

class SocketHandler {
    val openedRestaurants: ConcurrentHashMap<String, RestaurantInfo> = ConcurrentHashMap()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    suspend fun broadcastOrder(
        incoming: ReceiveChannel<Frame>,
        restaurantId: String,
        manageOrder: IManageOrderUseCase
    ) {
        val ownerSession = openedRestaurants[restaurantId]?.owner
        val orders = openedRestaurants[restaurantId]?.orders

        try {
            while (true) {
//                isRestaurantOpened(restaurantId, manageOrder).await().takeIf { it }.apply {
                    orders?.drop(1)?.collect { order ->
                        ownerSession?.send(order.toString())
                    }
//                } ?: throw MultiErrorException(listOf(RESTAURANT_CLOSED))
            }
        } catch (e: MultiErrorException) {
            ownerSession?.send(e.errorCodes.toString())
            ownerSession?.close()
        }
    }

    private suspend fun isRestaurantOpened(restaurantId: String, manageOrder: IManageOrderUseCase): Deferred<Boolean> {
        return scope.async { manageOrder.isRestaurantOpened(restaurantId) }
    }
}