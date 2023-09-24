package org.thechance.service_restaurant.data.collection.mapper

import org.thechance.service_restaurant.data.collection.OrderCollection
import org.thechance.service_restaurant.data.collection.relationModels.OrderWithRestaurant
import org.thechance.service_restaurant.domain.entity.OrderHistory
import org.thechance.service_restaurant.domain.entity.OrderedMeal

fun OrderWithRestaurant.toHistoryEntity() = OrderHistory(
    id = id.toString(),
    restaurantId = restaurant.id.toString(),
    restaurantName = restaurant.name,
    restaurantImage = restaurant.restaurantImage,
    totalPrice = totalPrice,
    status = orderStatus,
    createdAt = createdAt,
    meals = meals.map {it.toMealHistoryEntity()}
)

fun List<OrderWithRestaurant>.toHistoryEntity() = map { it.toHistoryEntity() }


fun OrderCollection.MealCollection.toMealHistoryEntity() = OrderedMeal(
    meadId = mealId.toString(),
    quantity = quantity,
    image = image,
    name = name
)

