package org.thechance.service_restaurant.api.utils

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOn
import org.thechance.service_restaurant.api.models.WebSocketOrder
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import java.util.concurrent.ConcurrentHashMap

class SocketHandler {

    val orders: ConcurrentHashMap<String, WebSocketOrder> = ConcurrentHashMap()

    suspend fun collectOrder(key: String) {

        val session = orders[key]?.session
        val order = orders[key]?.order

        try {
            order
                ?.drop(1)
                ?.flowOn(Dispatchers.IO)
                ?.collect { incomingOrder ->
                    session?.sendSerialized(incomingOrder)
                }
        } catch (e: MultiErrorException) {
            session?.send(e.errorCodes.toString())
            session?.close()
        } finally {
            orders.remove(key)
        }
    }
}