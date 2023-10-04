package org.thechance.service_restaurant.domain.entity.mapper

import org.thechance.service_restaurant.domain.entity.Cart
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.utils.currentDateTime


fun Cart.toOrder() = Order(
    id = "",
    userId = userId,
    restaurantId = restaurantId ?: "",
    restaurantName = restaurantName ?: "",
    restaurantImage = restaurantImage ?: "",
    meals = meals ?: emptyList(),
    createdAt = currentDateTime(),
    status = Order.Status.PENDING,
    totalPrice = totalPrice,
    currency = currency ?: ""
)