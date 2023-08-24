package data.remote.model

import domain.entity.Meal

data class MealDto(
    val id: String,
    val restaurantId: String,
    val name: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val price: Double? = null,
    val cuisines : List<String>? = null
)

fun List<MealDto>.toEntity(): List<Meal> = map { it.toEntity() }
fun MealDto.toEntity(): Meal {
    return Meal(
        id = id,
        restaurantId = restaurantId,
        name = name ?: "",
        description = description ?: "",
        price = price ?: 0.0,
        imageUrl = imageUrl ?: "",
        cuisines = emptyList(),
    )
}
