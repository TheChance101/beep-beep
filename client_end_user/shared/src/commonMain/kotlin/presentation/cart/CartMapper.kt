package presentation.cart

import domain.entity.Cart
import domain.entity.MealCart
import domain.entity.Price

fun CartUiState.toEntity(): Cart {
    return Cart(
        meals = meals.map { it.toEntity() },
        price = Price(totalPrice, currency),
        restaurantId = null,
        restaurantName = null,
        restaurantImageUrl = null
    )
}

fun CartMealUiState.toEntity(): MealCart {
    return MealCart(
        id = id,
        name = name,
        price = Price(price, currency),
        restaurantName = restaurantName,
        imageUrl = image,
        quality = count
    )
}
