package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.OrderHistoryDto
import org.thechance.service_restaurant.domain.entity.OrderHistory


fun OrderHistory.toHistoryDto() = OrderHistoryDto(
    id = id,
    restaurantId = restaurantId,
    restaurantImage = restaurantImage,
    restaurantName = restaurantName,
    meals = meals.toDto(),
    totalPrice = totalPrice,
    createdAt = createdAt,
)

fun List<OrderHistory>.toHistoryDto()= map { it.toHistoryDto() }