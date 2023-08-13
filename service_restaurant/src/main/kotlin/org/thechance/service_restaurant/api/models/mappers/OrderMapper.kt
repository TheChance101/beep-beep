package org.thechance.service_restaurant.api.models.mappers

import kotlinx.datetime.LocalDateTime
import org.thechance.service_restaurant.api.models.OrderDto
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.utils.OrderStatus


fun OrderDto.toEntity(): Order {
    return Order(
        id = id ?: "",
        userId = userId ?: "",
        restaurantId = restaurantId ?: "",
        meals = meals?.map {
            Meal(id = it, restaurantId = restaurantId ?: "", name = "", description = "", price = 0.0)
        } ?: emptyList(),
        totalPrice = totalPrice ?: 0.0,
        createdAt = createdAt?.let { LocalDateTime.parse(it) } ?: throw Throwable(""),
        orderStatus = OrderStatus.PENDING
    )
}

fun Order.toDto(): OrderDto {
    return OrderDto(
        id = id,
        userId = userId,
        restaurantId = restaurantId,
        meals = meals.map { it.id },
        totalPrice = totalPrice,
        createdAt = createdAt.toString(),
        orderStatus = orderStatus
    )
}