package data.remote.model

data class OrderDto(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<OrderMealDto>,
    val totalPrice: Double? = null,
    val createdAt: String? = null,
    val orderState: Int? = null
)



