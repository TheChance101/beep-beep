package data.remote.mapper

import data.remote.model.MealDto
import data.remote.model.MealModificationDto
import domain.entity.Meal
import domain.entity.MealModification

fun List<MealDto>.toEntity(): List<Meal> = map { it.toEntity() }

fun MealDto.toEntity() = Meal(
    id = id,
    restaurantId = restaurantId,
    name = name ?: "",
    description = description ?: "",
    price = price ?: 0.0,
    imageUrl = imageUrl ?: "",
    currency = currency ?: "$",
    cuisines = cuisines?.toEntity() ?: emptyList(),
)


fun MealModification.toDtoUpdate(): MealModificationDto {
    return MealModificationDto(
        id = id,
        restaurantId = restaurantId,
        name = name,
        description = description,
        price = price,
        cuisines = cuisines
    )
}

fun MealModification.toDto(): MealModificationDto {
    return MealModificationDto(
        restaurantId = restaurantId,
        cuisines = cuisines,
        description = description,
        name = name,
        price = price
    )
}

fun MealModificationDto.toEntity(): MealModification {
    return MealModification(
        id = id ?: "",
        restaurantId = restaurantId ?: "",
        name = name ?: "",
        description = description ?: "",
        price = price ?: 0.0,
        cuisines = emptyList(),
        image = byteArrayOf()
    )
}

