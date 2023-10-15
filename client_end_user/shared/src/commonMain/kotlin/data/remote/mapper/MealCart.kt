package data.remote.mapper

import data.remote.model.CartMealDto
import domain.entity.MealCart
import domain.entity.Price


fun CartMealDto.toEntity(restaurantName: String?, currency: String?) = MealCart(
    id = mealId ?: "",
    name = name ?: "",
    price = Price(price ?: 0.0, currency ?: ""),
    imageUrl = if (image.isNullOrBlank()) {
        "https://media.istockphoto.com/id/1457433817/photo/group-of-healthy-food-for-flexitarian-diet.jpg?s=1024x1024&w=is&k=20&c=iBBM7YTn5Rf-QhCd0kkvFaDNLV6Rb02iMQlS39LSSTI="
    } else {
        image
    },
    quality = quantity ?: 0,
    restaurantName = restaurantName ?: "",
)

fun List<CartMealDto>.toEntity(restaurantName: String?, currency: String?) =
    map { it.toEntity(restaurantName, currency) }
