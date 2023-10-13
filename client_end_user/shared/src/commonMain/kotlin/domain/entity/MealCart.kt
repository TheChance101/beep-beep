package domain.entity

data class MealCart(
    val id: String,
    val name: String,
    val restaurantName: String,
    val imageUrl: String,
    val quality: Int,
    val price: Price,
)
