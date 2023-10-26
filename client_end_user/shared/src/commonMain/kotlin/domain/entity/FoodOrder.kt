package domain.entity

data class FoodOrder(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val restaurantName: String,
    val restaurantImageUrl: String,
    val currency: String,
    val meals: List<MealCart>,
    val totalPrice: Double,
    val createdAt: String,
    val orderStatus: OrderStatusInRestaurant,
    val orderEstimatedTime: Int,
) {
    enum class OrderStatusInRestaurant(val statusCode: Int) {
        PENDING(0),
        APPROVED(1),
        IN_COOKING(2),
        DONE(3),
        CANCELED(4);

        companion object {
            fun getOrderStatus(statusCode: Int): OrderStatusInRestaurant {
                OrderStatusInRestaurant.values().forEach {
                    if (it.statusCode == statusCode) {
                        return it
                    }
                }
                return APPROVED
            }
        }
    }
}