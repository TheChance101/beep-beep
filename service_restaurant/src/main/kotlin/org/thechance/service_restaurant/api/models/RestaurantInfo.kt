package org.thechance.service_restaurant.api.models

import io.ktor.server.websocket.*

data class RestaurantInfo(
    val owner: WebSocketServerSession,
    val users: MutableList<MutableMap<String, WebSocketServerSession>>
)
