package domain.entity

data class Order(
    val id: String,
    val restaurantId: String,
    val restaurantName: String,
    val restaurantImageUrl: String,
    val meals: List<MealCart>,
    val createdAt: Long,
    val orderStatus: Int,
    val price: Price
)
