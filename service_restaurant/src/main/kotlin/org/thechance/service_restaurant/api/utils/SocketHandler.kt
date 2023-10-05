package org.thechance.service_restaurant.api.utils

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOn
import org.litote.kmongo.or
import org.thechance.service_restaurant.api.models.WebSocketRestaurant
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import java.util.concurrent.ConcurrentHashMap

class SocketHandler {

    val restaurants: ConcurrentHashMap<String, WebSocketRestaurant> = ConcurrentHashMap()

    suspend fun broadcastOrder(restaurantId: String) {

        val ownerSession = restaurants[restaurantId]?.ownerSession
        val orders = restaurants[restaurantId]?.orders

        try {
            orders
                ?.drop(1)
                ?.flowOn(Dispatchers.IO)
                ?.collect { order ->
                    ownerSession?.sendSerialized(order)
                    println("sssssss $order")
                }
        } catch (e: MultiErrorException) {
            ownerSession?.send(e.errorCodes.toString())
            ownerSession?.close()
        } finally {
            restaurants.remove(restaurantId)
        }
    }
}