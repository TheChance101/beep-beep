package data.remote.model

import domain.entity.Order
import domain.entity.OrderMeal

data class OrderDto(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<OrderMealDto>,
    val totalPrice: Double? = null,
    val createdAt: Long? = null,
    val orderState: Int? = null
)

fun List<OrderDto>.toOrderEntity(): List<Order> = map { it.toEntity() }
fun List<OrderMealDto>.toOrderMeaEntity(): List<OrderMeal> = map { it.toEntity() }
fun OrderDto.toEntity(): Order {
    return Order(
        id = id,
        userId = userId,
        restaurantId = restaurantId,
        meals = meals.toOrderMeaEntity(),
        totalPrice = totalPrice ?: 0.0,
        createdAt = createdAt ?: 0,
        orderState = orderState ?: 0
    )
}


