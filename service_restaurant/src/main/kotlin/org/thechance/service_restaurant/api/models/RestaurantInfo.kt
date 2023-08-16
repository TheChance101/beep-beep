package org.thechance.service_restaurant.api.models

import io.ktor.websocket.WebSocketSession

data class RestaurantInfo(
    val owner: WebSocketSession,
    val users: MutableList<MutableMap<String, WebSocketSession>>
)
