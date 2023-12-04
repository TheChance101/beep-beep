package data.remote.mapper

import data.remote.model.CartMealDto
import domain.entity.MealCart
import domain.entity.Price

fun CartMealDto.toEntity(restaurantName: String?, currency: String?) = MealCart(
    id = mealId ?: "",
    name = name ?: "",
    price = Price(price ?: 0.0, currency ?: ""),
    imageUrl = image ?: "",
    quality = quantity ?: 0,
    restaurantName = restaurantName ?: "",
)

fun List<CartMealDto>.toEntity(restaurantName: String?, currency: String?) =
    map { it.toEntity(restaurantName, currency) }
