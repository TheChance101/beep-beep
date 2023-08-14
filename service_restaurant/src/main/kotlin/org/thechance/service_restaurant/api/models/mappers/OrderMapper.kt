package org.thechance.service_restaurant.api.models.mappers

import kotlinx.datetime.LocalDateTime
import org.thechance.service_restaurant.api.models.OrderDto
import org.thechance.service_restaurant.api.models.OrderMealDto
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.entity.OrderMeal
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_TIME
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException


fun OrderDto.toEntity(): Order {
    return Order(
        id = id ?: "",
        userId = userId ?: "",
        restaurantId = restaurantId ?: "",
        meals = meals?.map { it.toEntity() } ?: emptyList(),
        totalPrice = totalPrice ?: 0.0,
        createdAt = createdAt?.let { LocalDateTime.parse(it) } ?: throw MultiErrorException(listOf(INVALID_TIME)),
        status = OrderStatus.PENDING.statusCode
    )
}

fun Order.toDto(): OrderDto {
    return OrderDto(
        id = id,
        userId = userId,
        restaurantId = restaurantId,
        meals = meals.map { it.toDto() },
        totalPrice = totalPrice,
        createdAt = createdAt.toString(),
        orderStatus = status
    )
}

fun OrderMealDto.toEntity(): OrderMeal {
    return OrderMeal(
        meadId = mealId,
        quantity = quantity
    )
}

fun OrderMeal.toDto(): OrderMealDto {
    return OrderMealDto(
        mealId = meadId,
        quantity = quantity
    )
}