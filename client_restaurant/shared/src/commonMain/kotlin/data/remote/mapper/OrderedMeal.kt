package data.remote.mapper

import data.remote.model.OrderMealDto
import domain.entity.OrderMeal

fun OrderMealDto.toEntity(): OrderMeal {
    return OrderMeal(
        id = id,
        mealImageUrl = mealImageUrl,
        mealName = mealName,
        quantity = quantity
    )
}