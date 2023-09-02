package domain.entity

import kotlinx.datetime.LocalDateTime
import presentation.base.RequestException

data class Order(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<Order.Meal>,
    val totalPrice: Double,
    val createdAt: LocalDateTime,
    val orderState: OrderState
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

enum class OrderState {
    PENDING,
    IN_COOKING,
    CANCELED,
    FINISHED;
}