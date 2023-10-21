package org.thechance.service_restaurant.api.utils

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOn
import org.thechance.service_restaurant.api.models.WebSocketOrder
import org.thechance.service_restaurant.domain.entity.Order
import java.util.concurrent.ConcurrentHashMap

class SocketHandler {

    val orders: ConcurrentHashMap<String, WebSocketOrder> = ConcurrentHashMap()

    suspend fun collectOrders(key: String) {

        val session = orders[key]?.session
        val order = orders[key]?.order

        try {
            order
                ?.drop(1)
                ?.flowOn(Dispatchers.IO)
                ?.collect { incomingOrder ->
                    session?.sendSerialized(incomingOrder)
                }
        } catch (e: Exception) {
            session?.close(CloseReason(CloseReason.Codes.NORMAL, e.message.toString()))
        } finally {
            orders.remove(key)
        }
    }

    suspend fun collectOrderByOrderId(orderId: String) {
        val session = orders[orderId]?.session
        val order = orders[orderId]?.order

        try {
            order
                ?.drop(1)
                ?.flowOn(Dispatchers.IO)
                ?.collect { incomingOrder ->
                    if (incomingOrder.orderStatus == Order.Status.DONE.statusCode
                        || incomingOrder.orderStatus == Order.Status.CANCELED.statusCode
                    ) {
                        session?.sendSerialized(incomingOrder)
                        endSession(orderId)
                    }
                    session?.sendSerialized(incomingOrder)
                }
        } catch (e: Exception) {
            session?.close(CloseReason(CloseReason.Codes.NORMAL, e.message.toString()))
        } finally {
            orders.remove(orderId)
        }
    }

    private suspend fun endSession(key: String, delayInMillis: Long = 3000) {
        val session = orders[key]?.session
        session?.let {
            delay(delayInMillis)
            it.close()
        }
        orders.remove(key)
    }
}