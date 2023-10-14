package domain.entity

data class Cart(
    val price: Price,
    val restaurantId: String?,
    val restaurantName: String?,
    val restaurantImageUrl: String?,
    val meals: List<MealCart>?
)
