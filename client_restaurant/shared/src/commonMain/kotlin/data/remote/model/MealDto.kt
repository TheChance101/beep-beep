package data.remote.model

import domain.entity.Meal

data class MealDto(
    val id: String? = null,
    val restaurantId: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Double? = null,
)
fun MealDto.toEntity(): Meal {
    return Meal(
        id = id,
        restaurantId = restaurantId,
        name = name,
        description = description,
        price = price,
    )
}