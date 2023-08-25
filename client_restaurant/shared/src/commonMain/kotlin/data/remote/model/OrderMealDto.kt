package data.remote.model

import domain.entity.OrderMeal

data class OrderMealDto(
    val id: String,
    val mealImageUrl: String,
    val mealName: String,
    val quantity: Int,
)

fun OrderMealDto.toEntity(): OrderMeal {
    return OrderMeal(
        id = id,
        mealImageUrl = mealImageUrl,
        mealName = mealName,
        quantity = quantity
    )
}