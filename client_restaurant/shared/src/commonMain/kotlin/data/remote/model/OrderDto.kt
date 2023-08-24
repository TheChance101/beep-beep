package data.remote.model

import domain.entity.Order
import domain.entity.OrderMeal
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import domain.entity.OrderState

data class OrderDto(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<OrderMealDto>,
    val totalPrice: Double? = null,
    val createdAt: String? = null,
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
        createdAt = createdAt?.let { LocalDateTime.parse(it) } ?: Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()),
        orderState = orderState?.let { OrderState.fromStatusCode(it) } ?: OrderState.PENDING
    )
}


