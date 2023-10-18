package data.remote.mapper

import data.remote.model.CartDto
import domain.entity.Cart
import domain.entity.Price

fun CartDto.toEntity() = Cart(
    meals = meals?.map { it.toEntity(restaurantName, currency) } ?: emptyList(),
    price = Price(totalPrice ?: 0.0, currency ?: ""),
    restaurantId = restaurantId,
    restaurantName = restaurantName,
    restaurantImageUrl = restaurantImage
)

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


fun Cart.toDto(): CartDto {
    return CartDto(
        meals = meals.map { it.toDto() },
        currency = currency
    )
}

fun CartMeal.toDto(): CartMealDto {
    return CartMealDto(
        mealId = id,
        currency = currency,
        restaurantName = restaurantName,
        name = name,
        image = image,
        quantity = count.toInt(),
        price = price
    )
}