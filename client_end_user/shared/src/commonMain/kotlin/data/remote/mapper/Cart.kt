package data.remote.mapper

import data.remote.model.CartDto
import data.remote.model.CartMealDto
import domain.entity.Cart
import domain.entity.MealCart
import domain.entity.Price

fun CartDto.toEntity(): Cart {
    return Cart(
        meals = meals?.map { it.toEntity() } ?: emptyList(),
        price = Price(totalPrice ?: 0.0, currency ?: "")
    )
}
