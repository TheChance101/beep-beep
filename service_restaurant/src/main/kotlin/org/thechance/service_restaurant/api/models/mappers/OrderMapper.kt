package org.thechance.service_restaurant.api.models.mappers


import kotlinx.datetime.LocalDateTime
import org.thechance.service_restaurant.api.models.OrderDto
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.utils.currentDateTime
import org.thechance.service_restaurant.domain.utils.fromEpochMilliseconds
import org.thechance.service_restaurant.domain.utils.toMillis


fun OrderDto.toEntity(): Order {
    return Order(
        id = id ?: "",
        userId = userId ?: "",
        restaurantId = restaurantId ?: "",
        meals = meals?.map { it.toEntity() } ?: emptyList(),
        totalPrice = totalPrice ?: 0.0,
        createdAt = createdAt?.let { LocalDateTime.fromEpochMilliseconds(it) } ?: currentDateTime(),
        status = Order.Status.getOrderStatus(orderStatus)
    )
}

fun Order.toDto(): OrderDto {
    return OrderDto(
        id = id,
        userId = userId,
        restaurantId = restaurantId,
        meals = meals.map { it.toDto() },
        totalPrice = totalPrice,
        createdAt = createdAt.toMillis(),
        orderStatus = status.statusCode
    )
}

fun OrderDto.MealDto.toEntity(): Order.Meal {
    return Order.Meal(
        meadId = mealId,
        quantity = quantity
    )
}

fun Order.Meal.toDto(): OrderDto.MealDto {
    return OrderDto.MealDto(
        mealId = meadId,
        quantity = quantity
    )
}