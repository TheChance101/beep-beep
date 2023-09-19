package domain.entity

data class Order(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val restaurantName: String,
    val restaurantImageUrl: String,
    val meals: List<Meal>,
    val totalPrice: Double,
    val createdAt: Long,
    val orderStatus: Int,
    val timeToArriveInMints: Int,
) {
    data class Meal(
        val mealId: String,
        val mealName: String,
        val quantity: Int
    )
}