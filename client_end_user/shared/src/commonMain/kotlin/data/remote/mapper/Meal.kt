package data.remote.mapper

import data.remote.model.MealDto
import domain.entity.Meal

fun MealDto.toEntity(): Meal {
    return Meal(
        id = id ?: "",
        name = name ?: "",
        description = description ?: "",
        price = price ?: 0.0,
        currency = currency ?: "",
        restaurantId = restaurantId ?: "",
        cuisines = cuisines ?: emptyList(),
        image = if (image.isNullOrBlank()) {
            "https://media.istockphoto.com/id/1457433817/photo/group-of-healthy-food-for-flexitarian-diet.jpg?s=1024x1024&w=is&k=20&c=iBBM7YTn5Rf-QhCd0kkvFaDNLV6Rb02iMQlS39LSSTI="
        } else {
            image
        },
        restaurantName = restaurantName ?: "restaurant Name"
    )
}

fun List<MealDto>.toEntity() = map { it.toEntity() }
