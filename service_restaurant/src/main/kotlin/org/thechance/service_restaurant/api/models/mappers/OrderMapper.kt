package org.thechance.service_restaurant.api.models.mappers


import org.thechance.service_restaurant.api.models.OrderDto
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.utils.toMillis

fun Order.toDto() = OrderDto(
    id = id,
    userId = userId,
    restaurantId = restaurantId,
    restaurantName = restaurantName,
    restaurantImage = restaurantImage,
    meals = meals.toDto(),
    totalPrice = totalPrice,
    currency = currency,
    createdAt = createdAt.toMillis(),
    orderStatus = status.statusCode
)






