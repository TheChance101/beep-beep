package domain.entity

data class CartMeal(
    val id: String,
    val name: String,
    val price: Double,
    val currency: String,
    val restaurantName: String,
    val image: String,
    val count: Long
)
