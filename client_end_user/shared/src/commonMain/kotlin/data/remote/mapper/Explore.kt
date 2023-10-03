package data.remote.mapper

import data.remote.model.MealRestaurantDto
import domain.entity.Explore

fun MealRestaurantDto.toEntity() = Explore(
    meals = meals.toEntity(),
    restaurants = restaurants.toEntity()
)
