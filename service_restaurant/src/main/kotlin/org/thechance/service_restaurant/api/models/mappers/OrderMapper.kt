package org.thechance.service_restaurant.api.models.mappers


import kotlinx.datetime.LocalDateTime
import org.thechance.service_restaurant.api.models.OrderDto
import org.thechance.service_restaurant.api.models.OrderedMealDto
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.entity.OrderedMeal
import org.thechance.service_restaurant.domain.utils.currentDateTime
import org.thechance.service_restaurant.domain.utils.fromEpochMilliseconds
import org.thechance.service_restaurant.domain.utils.toMillis

fun OrderDto.toEntity() = Order(
    id = id ?: "",
    userId = userId ?: "",
    restaurantId = restaurantId ?: "",
    meals = meals?.toEntity() ?: emptyList(),
    totalPrice = totalPrice ?: 0.0,
    createdAt = createdAt?.let { LocalDateTime.fromEpochMilliseconds(it) } ?: currentDateTime(),
    status = Order.Status.getOrderStatus(orderStatus)
)


fun Order.toDto() = OrderDto(
    id = id,
    userId = userId,
    restaurantId = restaurantId,
    meals = meals.toDto(),
    totalPrice = totalPrice,
    createdAt = createdAt.toMillis(),
    orderStatus = status.statusCode
)






