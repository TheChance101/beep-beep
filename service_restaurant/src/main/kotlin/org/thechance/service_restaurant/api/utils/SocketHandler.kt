package org.thechance.service_restaurant.api.utils

import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOn
import org.thechance.service_restaurant.api.models.Restaurant
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.RESTAURANT_CLOSED
import java.util.concurrent.ConcurrentHashMap

class SocketHandler {

    val restaurants: ConcurrentHashMap<String, Restaurant> = ConcurrentHashMap()

    suspend fun broadcastOrder(restaurantId: String) {

        val ownerSession = restaurants[restaurantId]?.ownerSession
        val orders = restaurants[restaurantId]?.orders

        try {
            while (true) {
                orders
                    ?.drop(1)
                    ?.flowOn(Dispatchers.IO)
                    ?.catch { throw MultiErrorException(listOf(RESTAURANT_CLOSED)) }
                    ?.collect { order -> ownerSession?.send(order.toString()) }
            }
        } catch (e: MultiErrorException) {
            ownerSession?.send(e.errorCodes.toString())
            ownerSession?.close()
        }
    }
}