package domain.entity

data class Cart(
    val meals: List<CartMeal>,
    val totalPrice: Double,
    val currency: String
)
