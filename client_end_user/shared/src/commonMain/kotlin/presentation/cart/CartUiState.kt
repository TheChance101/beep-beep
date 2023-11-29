package presentation.cart

import domain.entity.Cart
import domain.entity.MealCart
import presentation.base.ErrorState

data class CartUiState(
    val meals: List<CartMealUiState> = emptyList(),
    val currency: String = "",
    val showToast: Boolean = false,
    val isOrderNowLoading: Boolean = false,
    val orderError: ErrorState? = null,
) {
    val totalPrice: Double get() = meals.sumOf { it.price * it.count }
}

data class CartMealUiState(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val currency: String = "",
    val restaurantName: String = "",
    val image: String = "",
    val count: Int = 0,
)

fun Cart.toUiState() = CartUiState(
    meals = meals?.map { it.toUiState() } ?: emptyList(),
    currency = price.currency
)


fun MealCart.toUiState(): CartMealUiState {
    return CartMealUiState(
        id = id,
        name = name,
        price = price.value,
        currency = price.currency,
        image = imageUrl,
        count = quality
    )
}
