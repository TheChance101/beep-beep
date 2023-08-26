package domain.entity

import kotlinx.datetime.LocalDateTime

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
    )
}

enum class OrderState {
    PENDING,
    IN_COOKING,
    CANCELED,
    FINISHED;
}

//enum class OrderState(val statusCode: Int) {
//    PENDING(0),
//    IN_COOKING(1),
//    CANCELED(2),
//    FINISHED(3);
//
//    companion object {
//        private val map = values().associateBy(OrderState::statusCode)
//        fun fromStatusCode(code: Int) = map[code] ?: PENDING
//    }
//}
