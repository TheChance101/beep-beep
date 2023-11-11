package data.remote.mapper

import data.remote.model.OrderDto
import domain.entity.Order
import domain.entity.OrderStatus
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
        orderState = OrderStatus.getOrderStatus(orderStatus ?: Constant.PENDING_ORDER)

    )
}