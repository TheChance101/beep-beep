package data.remote.mapper

import data.remote.model.CartDto
import data.remote.model.CartMealDto
import domain.entity.Cart
import domain.entity.CartMeal

fun CartDto.toEntity(): Cart {
    return Cart(
        meals = meals?.map { it.toEntity() } ?: emptyList(),
        totalPrice = totalPrice ?: 0.0,
        currency = currency ?: ""
    )
}

fun CartMealDto.toEntity(): CartMeal {
    return CartMeal(
        id = mealId ?: "",
        name = name ?: "",
        price = price ?: 0.0,
        currency = currency ?: "",
        restaurantName = restaurantName ?: "",
        image = image ?: "",
        count = quantity?.toLong() ?: 0
    )
}