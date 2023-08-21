package data.remote.model

import domain.entity.OrderMeal

data class OrderMealDto(
    val mealId: String,
    val quantity: Int
)

fun OrderMealDto.toEntity(): OrderMeal {
    return OrderMeal(
        mealId = mealId,
        quantity = quantity
    )
}