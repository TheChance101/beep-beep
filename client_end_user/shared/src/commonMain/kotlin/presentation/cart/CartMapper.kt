package presentation.cart

import domain.entity.Cart
import domain.entity.CartMeal

fun CartUiState.toEntity(): Cart {
    return Cart(
        meals = meals.map { it.toEntity() },
        currency = currency,
        totalPrice = totalPrice
    )
}

fun CartMealUiState.toEntity(): CartMeal {
    return CartMeal(
        id = id,
        name = name,
        price = price,
        currency = currency,
        restaurantName = restaurantName,
        image = image,
        count = count
    )
}
