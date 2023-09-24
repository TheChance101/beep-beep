package presentation.cart

import domain.entity.Cart
import domain.entity.CartMeal

data class CartUiState(
    val meals: List<CartMealUiState> = emptyList(),
    val totalPrice: Double = 0.0,
    val currency: String = ""
)

data class CartMealUiState(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val currency: String = "",
    val restaurantName: String = "",
    val image: String = "",
    val count: Long = 0
)

fun Cart.toUiState(): CartUiState {
    return CartUiState(
        meals = meals.map { it.toUiState() },
        totalPrice = totalPrice,
        currency = currency
    )
}

fun CartMeal.toUiState(): CartMealUiState {
    return CartMealUiState(
        id = id,
        name = name,
        price = price,
        currency = currency,
        restaurantName = restaurantName,
        image = image,
        count = count
    )
}
