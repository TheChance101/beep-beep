package data.remote.mapper

import data.remote.model.MealDto

fun MealDto.toEntity() = domain.entity.Meal(
    id = id,
    name = name,
    price = price,
    image = image,
    discount = discount,
)