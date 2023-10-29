package data.remote.mapper

import data.remote.model.CartDto
import data.remote.model.CartMealDto
import domain.entity.Cart
import domain.entity.MealCart
import domain.entity.Price

fun CartDto.toEntity() = Cart(
    meals = meals?.map { it.toEntity(restaurantName, currency) } ?: emptyList(),
    price = Price(totalPrice ?: 0.0, currency ?: ""),
    restaurantId = restaurantId,
    restaurantName = restaurantName,
    restaurantImageUrl = restaurantImage
)

fun CartMealDto.toEntity(): MealCart {
    return MealCart(
        id = mealId ?: "",
        name = name ?: "",
        price = Price(price ?: 0.0, currency ?: ""),
        restaurantName = restaurantName ?: "",
        imageUrl = image ?: "",
        quality = quantity ?: 0
    )
}


fun Cart.toDto(): CartDto {
    return CartDto(
        meals = meals?.map { it.toDto() },
        currency = price.currency,
        totalPrice = price.value,
        restaurantId = restaurantId,
        restaurantName = restaurantName,
        restaurantImage = restaurantImageUrl
    )
}

fun MealCart.toDto(): CartMealDto {
    return CartMealDto(
        mealId = id,
        currency = price.currency,
        restaurantName = restaurantName,
        name = name,
        image = imageUrl,
        quantity = quality,
        price = price.value
    )
}