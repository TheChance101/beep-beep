package org.thechance.service_restaurant.api.models.mappers

import org.thechance.service_restaurant.api.models.CartDto
import org.thechance.service_restaurant.api.models.OrderedMealDto
import org.thechance.service_restaurant.domain.entity.Cart
import org.thechance.service_restaurant.domain.entity.OrderedMeal
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException


fun Cart.toDto() = CartDto(
    id = id,
    restaurantId = restaurantId,
    restaurantImage= restaurantImage,
    restaurantName = restaurantName,
    meals = meals?.toDto(),
    totalPrice = totalPrice,
    currency = currency
)

fun CartDto.toDomain(userId: String): Cart {
    return Cart(
        id = id ?: throw MultiErrorException(listOf(INVALID_REQUEST_PARAMETER)),
        userId = userId,
        restaurantId = this.restaurantId,
        meals = meals?.map { it.toDomain() } ?: emptyList()
    )
}

fun OrderedMealDto.toDomain(): OrderedMeal {
    return OrderedMeal(
        meadId = mealId ?: throw MultiErrorException(listOf(INVALID_REQUEST_PARAMETER)),
        name = name ?: throw MultiErrorException(listOf(INVALID_REQUEST_PARAMETER)),
        image = image ?: "",
        quantity = quantity ?: 1,
        price = price ?: throw MultiErrorException(listOf(INVALID_REQUEST_PARAMETER)),
    )
}