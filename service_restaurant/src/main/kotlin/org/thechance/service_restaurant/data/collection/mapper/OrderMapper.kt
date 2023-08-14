package org.thechance.service_restaurant.data.collection.mapper

import kotlinx.datetime.LocalDateTime
import org.thechance.service_restaurant.data.collection.OrderCollection
import org.thechance.service_restaurant.data.collection.RestaurantCollection
import org.thechance.service_restaurant.data.utils.toUUID
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.utils.OrderStatus


fun Order.toCollection(): OrderCollection {
    return OrderCollection(
        id = id.toUUID(),
        userId = userId.toUUID(),
        restaurantId = restaurantId.toUUID(),
        meals = meals.map { it.id.toUUID() },
        totalPrice = totalPrice,
        createdAt = createdAt.toString(),
        orderStatus = orderStatus.statusCode
    )
}

fun OrderCollection.toEntity(): Order {
    return Order(
        id = id.toString(),
        userId = userId.toString(),
        restaurantId = restaurantId.toString(),
        meals = meals.map {
            Meal(
                id = it.toString(),
                restaurantId = restaurantId.toString(),
                name = "",
                description = "",
                price = 0.0
            )
        },
        totalPrice = totalPrice,
        createdAt = createdAt.let { LocalDateTime.parse(it) },
        orderStatus = OrderStatus.getOrderStatus(orderStatus)
    )
}

fun List<OrderCollection>.toEntity(): List<Order> = map { it.toEntity() }
