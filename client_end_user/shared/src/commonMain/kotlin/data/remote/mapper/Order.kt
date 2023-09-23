package data.remote.mapper

import data.remote.model.OrderDto
import domain.entity.Order

fun OrderDto.toOrderEntity(): Order {
    return Order(
        id = id,
        userId = userId,
        restaurantId = restaurantId,
        restaurantName = restaurantName,
        restaurantImageUrl = restaurantImageUrl ?: "https://takethemameal.com/files_images_v2/stam.jpg",
        meals = meals.toEntity(),
        totalPrice = totalPrice ?: 0.0,
        createdAt = createdAt ?: 0L,
        orderStatus = orderStatus,
        timeToArriveInMints = timeToArriveInMints ?: 0,
    )
}

fun OrderDto.MealDto.toMealEntity(): Order.Meal {
    return Order.Meal(
        mealId = mealId,
        mealName = mealName,
        quantity = quantity
    )
}

fun List<OrderDto.MealDto>.toEntity() = map { it.toMealEntity() }