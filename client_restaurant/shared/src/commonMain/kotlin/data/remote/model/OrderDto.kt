package data.remote.model

import domain.entity.Order
import domain.entity.OrderState
import domain.utils.Constant
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import presentation.base.RequestException

data class OrderDto(
    val id: String,
    val userId: String,
    val restaurantId: String,
    val meals: List<MealDto>,
    val totalPrice: Double? = null,
    val createdAt: String? = null,
    val orderState: Int
) {
    data class MealDto(
        val id: String,
        val mealImageUrl: String,
        val mealName: String,
        val quantity: Int,
    )
}

fun OrderDto.MealDto.toEntity(): Order.Meal {
    return Order.Meal(
        id = id,
        mealImageUrl = mealImageUrl,
        mealName = mealName,
        quantity = quantity
    )
}

fun List<OrderDto>.toOrderEntity(): List<Order> = map { it.toEntity() }
fun List<OrderDto.MealDto>.toOrderMeaEntity(): List<Order.Meal> = map { it.toEntity() }
fun OrderDto.toEntity(): Order {
    return Order(
        id = id,
        userId = userId,
        restaurantId = restaurantId,
        meals = meals.toOrderMeaEntity(),
        totalPrice = totalPrice ?: 0.0,
        createdAt = createdAt?.let { LocalDateTime.parse(it) } ?: Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault()),
        orderState = when (orderState) {
            Constant.PENDING_ORDER -> OrderState.PENDING
            Constant.IN_COOKING_ORDER -> OrderState.IN_COOKING
            Constant.FINISHED_ORDER -> OrderState.FINISHED
            Constant.CANCELED_ORDER -> OrderState.CANCELED
            else -> throw RequestException()
        }
    )
}


