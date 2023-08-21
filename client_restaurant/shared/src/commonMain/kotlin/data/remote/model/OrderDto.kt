package data.remote.model

import domain.entity.Order
import domain.entity.OrderMeal

data class OrderDto(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<OrderMealDto>,
    val totalPrice: Double? = null,
    val createdAt: String? = null,
    var orderStatus: Int? = null
)

fun List<OrderDto>.toEntity(): List<Order> = map { it.toEntity() }
fun List<OrderMealDto>.toEntity(): List<OrderMeal> = map { it.toEntity() }
fun OrderDto.toEntity(): Order {
    return Order(
        id = id,
        userId = userId,
        restaurantId = restaurantId,
        meals = meals.toEntity(),
        totalPrice = totalPrice ?: 0.0,
        createdAt = createdAt ?: "",
        orderStatus = orderStatus ?: 0
    )
}


