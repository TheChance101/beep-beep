package domain.entity

data class Order(
    val id: String? = null,
    val userId: String? = null,
    val restaurantId: String? = null,
    val meals: List<OrderMeal>? = null,
    val totalPrice: Double? = null,
    val createdAt: String? = null,
    val orderStatus: Int = 0
)
