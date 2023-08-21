package org.thechance.service_restaurant.api.models

import io.ktor.websocket.*
import kotlinx.coroutines.flow.MutableStateFlow

data class RestaurantInfo(val owner: WebSocketSession){
    val orders = MutableStateFlow(OrderDto())
}