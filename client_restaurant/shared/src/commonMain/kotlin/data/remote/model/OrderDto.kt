package data.remote.model

import domain.entity.Order
import domain.entity.OrderMeal

data class OrderDto(
    val id: String? = null,
    val userId: String? = null,
    val restaurantId: String? = null,
    val meals: List<OrderMealDto>? = null,
    val totalPrice: Double? = null,
    val createdAt: String? = null,
    val orderStatus: Int = 0
)

fun List<OrderMealDto>.toEntity(): List<OrderMeal> = map { it.toEntity() }
fun OrderDto.toEntity(): Order {
    return Order(
        id = id,
        userId = userId,
        restaurantId = restaurantId,
        meals = meals?.toEntity()
    )
}


