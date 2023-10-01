package org.thechance.service_restaurant.data.collection.relationModels

import kotlinx.serialization.Serializable
import org.thechance.service_restaurant.data.collection.OrderCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection

@Serializable
data class OrderMeals(
    val restaurants: RestaurantCollection,
    val orders: OrderCollection,
)
