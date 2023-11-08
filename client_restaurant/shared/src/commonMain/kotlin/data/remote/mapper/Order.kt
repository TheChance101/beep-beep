package data.remote.mapper

import data.remote.model.OrderDto
import domain.entity.Order
import domain.entity.OrderState
import domain.utils.toLocalDateTime
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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
        createdAt = createdAt?.let { createdAt ->
            createdAt.toLocalDateTime()
        } ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
        orderState = when (orderState) {
            Constant.PENDING_ORDER -> OrderState.PENDING
            Constant.IN_COOKING_ORDER -> OrderState.IN_COOKING
            Constant.FINISHED_ORDER -> OrderState.FINISHED
            Constant.CANCELED_ORDER -> OrderState.CANCELED
            else -> OrderState.PENDING
        }
    )
}
 object Constant {
    const val PENDING_ORDER = 0
    const val IN_COOKING_ORDER = 1
    const val FINISHED_ORDER = 2
    const val CANCELED_ORDER = 3
}