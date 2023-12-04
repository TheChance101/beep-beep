package domain.entity

import kotlinx.datetime.LocalDateTime

data class Order(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<Meal>,
    val totalPrice: Double,
    val currency: String,
    val createdAt: LocalDateTime,
    val orderState: OrderStatus,
) {
    data class Meal(
        val id: String,
        val mealImageUrl: String,
        val mealName: String,
        val quantity: Int,
    ) {
        init {
            if (quantity < 0) {
                throw IllegalArgumentException()
            }
        }
    }
}

